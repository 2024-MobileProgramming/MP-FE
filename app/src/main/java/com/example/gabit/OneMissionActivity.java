package com.example.gabit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OneMissionActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 2;

    ImageButton cameraButton;
    ImageButton heartButton;
    ImageButton backButton;
    LinearLayout stars;
    Button proofButton;
    TextView linkTextView;

    private String userId; // 로그인 유저 아이디 (조회자)
    private String ownerId; // 미션 주인 아이디
    private int missionId;
    private boolean isMyMission;
    private boolean isVerificatedByViewer;

    private MissionService missionService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onemission);

        missionService = new MissionService();

        cameraButton = findViewById(R.id.cameraButton);
        heartButton = findViewById(R.id.heart);
        backButton = findViewById(R.id.backButton);
        stars = findViewById(R.id.stars);
        proofButton = findViewById(R.id.proofButton);
        linkTextView = findViewById(R.id.linkTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", ""); // 로그인 유저 아이디 가져오기

        // Intent에서 미션 정보 가져오기
        ownerId = getIntent().getStringExtra("ownerId"); // 미션 주인 아이디
        missionId = getIntent().getIntExtra("missionId", -1);

        isMyMission = userId.equals(ownerId); // 로그인 유저가 미션 주인인지 확인

        Log.e("SharedPreferences : ", sharedPreferences.toString());
        Log.e("userId : ", userId);
        Log.e("ownerId : ", ownerId);
        Log.e("missionId : ", String.valueOf(missionId));
        Log.e("isMyMission : ", String.valueOf(isMyMission));

        updateUiBasedOnOwnership(); // UI 업데이트 (내 미션인지 아닌지에 따라)

        fetchMissionDetails(); // 미션 상세 정보 가져오기

        backButton.setOnClickListener(v -> finish()); // 뒤로가기 버튼 클릭 리스너

        // 카메라 버튼 클릭 리스너 (내 미션인 경우만)
        cameraButton.setOnClickListener(v -> {
            if (isMyMission) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkPermissionAndOpenGallery();
                }
            }
        });

        // 좋아요 버튼 클릭 리스너 (내 미션이 아니고 인증하지 않은 경우만)
        heartButton.setOnClickListener(v -> {
            if (!isMyMission && !isVerificatedByViewer) {
                verificateMission();
            }
        });

        // 증명 사진 제출 버튼 클릭 리스너 (내 미션인 경우만)
        proofButton.setOnClickListener(v -> {
            if (isMyMission) {
                // proofButton 비활성화
                proofButton.setEnabled(false);

                // tag 값이 null이 아니면 함수 실행하지 않음
                if (cameraButton.getTag() != null) {
                    Uri selectedImageUri = (Uri) cameraButton.getTag();
                    proofMission(selectedImageUri);
                }
            }
        });

        // 링크 텍스트뷰 클릭 리스너
        linkTextView.setOnClickListener(v -> {
            String url = linkTextView.getText().toString();
            if (!url.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    // 미션 상세 정보 가져오기
    private void fetchMissionDetails() {
        missionService.getOneMission(ownerId, missionId, userId, new MissionService.MissionDetailCallback() {
            @Override
            public void onSuccess(MissionDetail missionDetail) {
                Log.d("ownerID : ", ownerId);
                Log.d("missionID : ", String.valueOf(missionId));
                Log.d("userId : ", userId);

                ((TextView) findViewById(R.id.oneTitle)).setText(missionDetail.getMissionTitle());
                ((TextView) findViewById(R.id.oneDesc)).setText(missionDetail.getMissionDescription());

                // 이미지가 있으면 이미지 설정
                if (missionDetail.getImage() != null && !missionDetail.getImage().equals("null")) {
                    byte[] decodedString = Base64.decode(missionDetail.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    cameraButton.setImageBitmap(decodedByte);
                }

                drawStar(missionDetail.getVerificationNumber());

                if (missionDetail.getUrl() != null) {
                    linkTextView.setText(missionDetail.getUrl());
                }

                isVerificatedByViewer = missionDetail.isVerificatedByViewer();
                updateHeartButton();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("OneMissionActivity", errorMessage);
                Toast.makeText(OneMissionActivity.this, "미션 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 미션 주인 여부에 따라 UI 업데이트
    private void updateUiBasedOnOwnership() {
        if (isMyMission) {
            cameraButton.setVisibility(View.VISIBLE);
            proofButton.setVisibility(View.VISIBLE);
            heartButton.setVisibility(View.GONE);
        } else {
            cameraButton.setVisibility(View.GONE);
            proofButton.setVisibility(View.GONE);
            heartButton.setVisibility(View.VISIBLE);
        }
    }

    // 별 표시 그리기
    private void drawStar(int count) {
        stars.removeAllViews();
        int maxStars = Math.min(count, 3);
        for (int i = 0; i < maxStars; i++) {
            ImageView starImage = new ImageView(this);
            starImage.setImageResource(R.drawable.star);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(5, 0, 5, 0);
            starImage.setLayoutParams(layoutParams);
            stars.addView(starImage);
        }
    }

    // 갤러리 접근 권한 요청 및 갤러리 열기
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
        } else {
            openFilePicker(); // 권한이 이미 허용된 경우 바로 갤러리 열기
        }
    }

    // 갤러리 열기
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    // 갤러리에서 이미지 선택 결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("onActivityResult", "requestCode: " + requestCode + ", resultCode: " + resultCode);
        if (requestCode == REQUEST_PERMISSION && resultCode == RESULT_OK) {
            openFilePicker();
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // proofButton에 URI 설정
                cameraButton.setImageURI(selectedImageUri);
                Toast.makeText(OneMissionActivity.this, "이미지가 선택되었습니다.", Toast.LENGTH_SHORT).show();

                // 선택된 이미지 URI 로그 출력
                Log.e("Image URI: ", selectedImageUri.toString());

                // **cameraButton의 tag에 URI 저장**
                cameraButton.setTag(selectedImageUri);

                // proofButton 활성화
                proofButton.setEnabled(true);
            }
        }
    }

    // 미션 인증 (좋아요)
    private void verificateMission() {
        VerificationRequest request = new VerificationRequest(Integer.parseInt(userId), Integer.parseInt(ownerId), missionId);
        missionService.verificateMission(request, new MissionService.ApiCallback() {
            @Override
            public void onSuccess() {
                isVerificatedByViewer = true;
                updateHeartButton();
                Toast.makeText(OneMissionActivity.this, "미션을 인증했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("OneMissionActivity", errorMessage);
                Toast.makeText(OneMissionActivity.this, "미션 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 미션 증명 사진 제출
    private void proofMission(Uri imageUri) {
        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(bitmap);

            // 인코딩된 이미지 로그 출력 (missionService.proofMission 호출 전에 한 번만 출력)
            Log.e("Encoded Image : ", encodedImage);

            ProofRequest request = new ProofRequest(Integer.parseInt(userId), missionId, encodedImage);
            missionService.proofMission(request, new MissionService.MissionDetailResponseCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(OneMissionActivity.this, "미션 인증을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    // TODO: 미션 목록 업데이트 등 필요한 작업 수행

                    // **proofMission 실행 후 tag 값 초기화**
                    cameraButton.setTag(null);
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("OneMissionActivity", errorMessage);
                    Toast.makeText(OneMissionActivity.this, "미션 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();

                    // **proofMission 실행 후 tag 값 초기화**
                    cameraButton.setTag(null);
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "이미지 파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 이미지를 Base64로 인코딩하는 메서드
    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    // 좋아요 버튼 상태 업데이트
    private void updateHeartButton() {
        if (isVerificatedByViewer) {
            heartButton.setImageResource(R.drawable.fillheart);
        } else {
            heartButton.setImageResource(R.drawable.emptyheart);
        }
    }

    // 미션 상세 정보 응답 모델 (MissionDetailResponse)
    public static class MissionDetailResponse {
        private int status;
        private String message;
        private  MissionDetail data;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public MissionDetail getData() {
            return data;
        }
    }

    // 미션 상세 정보 모델 (MissionDetail)
    public static class MissionDetail {
        private int missionId;
        private String image;
        private int verificationNumber;
        private String missionTitle;
        private String missionDescription;
        private String url;
        private boolean isVerificatedByViewer;

        public int getMissionId() {
            return missionId;
        }

        public String getImage() {
            return image;
        }

        public int getVerificationNumber() {
            return verificationNumber;
        }

        public String getMissionTitle() {
            return missionTitle;
        }

        public String getMissionDescription() {
            return missionDescription;
        }

        public String getUrl() {
            return url;
        }

        public boolean isVerificatedByViewer() {
            return isVerificatedByViewer;
        }
    }

}