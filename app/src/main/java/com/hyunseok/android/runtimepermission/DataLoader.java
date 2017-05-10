package com.hyunseok.android.runtimepermission;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-02-01.
 */

public class DataLoader {

    private ArrayList<Contact> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context) {
        this.context = context;
    }

    // 생성자 함수
//    public DataLoader() {
//        datas = new ArrayList<>();
//        load();
//    }

    public ArrayList<Contact> get() {
        return datas;
    }

    public void load() {

        // 2. 주소록에서 가져올 데이터 컬럼명을 정의.
        String projections[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  // 데이터의 ID
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,    // 이름
                ContactsContract.CommonDataKinds.Phone.NUMBER   // 전화번호
        };

        // 1. 주소록에 접근하기 위해 Content Resolver를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 3. Content Resolver로 쿼리한 데이터를 커서에 담게된다.
        // 전화번호 : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 : ContactsContract.Contacts.CONTENT_URI
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        , projections, null, null, null ); // query(데이터의 주소, 프로젝션(가져올 데이터 컬럼명 배열), 조건절에 들어가는 컬렴명들 지정, 지정된 컬럼명과 매핑되는 실제 조건값, 정렬)


        if(cursor != null) {
            // 4. 커서에 넘어온 데이터가 있다면 반복문을 돌면서 datas에 담아준다.
            while( cursor.moveToNext() ) {

                Contact contact = new Contact();

                int idx = cursor.getColumnIndex(projections[0]);
                contact.setId(cursor.getInt(idx));

                idx = cursor.getColumnIndex(projections[1]);
                contact.setName(cursor.getString(idx));

                idx = cursor.getColumnIndex(projections[2]);
                contact.addTel(cursor.getString(idx));

                datas.add(contact);
            }
            // 사용 후 close를 해주지 않으면 메모리 누수가 발생할 수 있다.
            cursor.close();
        }
    }
}
