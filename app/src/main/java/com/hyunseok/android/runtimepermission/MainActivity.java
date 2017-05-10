package com.hyunseok.android.runtimepermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> datas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버전체크해서 마시멜로우보다 낮으면 런타임권한 체크를 하지 않는다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            loadData();
        }
    }

    private final int REQ_CODE = 100;


    // 1. 권한 체크
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 Annotati on
    private void checkPermission() {
        // 1.1 런타임 권한 체크
        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ) { // Permission이 없을 경우. 쓰기만하면 읽기도 자동으로 가능.

            // 1.2 요청할 권한 목록 작성
            String perArr[] = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS };

            // 1.3 시스템에 권한 요청.
            requestPermissions(perArr, REQ_CODE);

        } else { // Permission이 있을 경우.
            loadData();
        }
    }

    // 2. 권한 체크 후 콜백(사용자가 확인 후 시스템이 호출하는 함수)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE) {
            // 배열에 넘긴 런타임권한을 체크해서 승인이 된 경우 // 두개의 권한 [0] [1] 모두 그랜트 되었을 경우
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED ) {
                loadData();
            } else {
                Toast.makeText(this, "권한을 실행하지 않으면 프로그램이 실행되지 않습니다.", Toast.LENGTH_SHORT).show();

                // 선택 종료,
            }
        }
    }

    // 3. 데이터 읽어오기(기능 수행)
    private void loadData() {
        //Toast.makeText(this, "실행한다.", Toast.LENGTH_SHORT).show();

        // 데이터를 불러온다.
        DataLoader loader = new DataLoader(this);
        //datas = loader.get(); // 생성자함수에서 load()함수를 호출 했을 때.
        loader.load();
        datas = loader.get();

        // 1. Recycler View(ViewHolder까지 있음)
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);

        // 2. Adapter 생성하기
        RecyclerAdapter adapter = new RecyclerAdapter(datas, this);

        // 3. Recycler View에 Adapter 세팅하기
        rv.setAdapter(adapter);

        // 4. Recycler View 매니저 등록하기(View의 모양(Grid, 일반, 비대칭Grid)을 결정한다.)
        rv.setLayoutManager(new LinearLayoutManager(this));


    }
}
