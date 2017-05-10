package com.hyunseok.android.runtimepermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * 1. 홀더에 사용하는 위젯을 모두 정의한다.
 * 2. getItemCount에 데이터 개수 전달.
 * 3. onCreateViewHolder에서 뷰 아이템 생성.
 * 4. onBindViewHolder를 통해 Logic을 구현한다.
 * Created by Administrator on 2017-02-01.
 *
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    ArrayList<Contact> datas;
    Context context;

    // 생성자 함수
    public RecyclerAdapter(ArrayList<Contact> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false); // context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) 와 같다.
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        // 1. 데이터를 행 단위로 꺼낸다.
        final Contact contact = datas.get(position); // 멤버변수가 아닌 지역변수를 참조할땐 상수로 가져와야함.

        // 2. 홀더에 데이터를 세팅한다.
        holder.tv1.setText(contact.getId() + "");
        holder.tv2.setText(contact.getName());
        holder.tv3.setText(contact.getPhoneNum()+"");

        // 3. 액션을 정의한다. (리스너 세팅)
        // ImageButton에 클릭리스너를 달아서 동작시킨다.

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     *
     */
    // Recycler View에서 사용하는 뷰홀더.
    // 이 뷰홀더를 사용하는 Adapter는 generic으로 선언된 부모객체를 상속받아 구현해야 한다.
    public class Holder extends RecyclerView.ViewHolder {

        TextView tv1, tv2, tv3;
        CardView cardView;

        ImageButton imgbtn;


        public Holder(View itemView) {
            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3 = (TextView) itemView.findViewById(R.id.tv3);
            imgbtn = (ImageButton) itemView.findViewById(R.id.imgbtn);

            cardView = (CardView) itemView.findViewById(R.id.cardView);

            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String value = tv3.getText().toString();

                    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) { // Permission이 없을 경우. 쓰기만하면 읽기도 자동으로 가능.
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + value)); // Intent.ACTION_CALL, DIAL
                            context.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + value)); // Intent.ACTION_CALL, DIAL
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
