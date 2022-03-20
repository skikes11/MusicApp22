package com.android.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeSongLeft, txtTimeSongRight;
    SeekBar skBarSong;
    ImageButton btnPrev,btnPlay, btnLoop, btnNext;
    ArrayList<Song> listSongs;
    MediaPlayer mediaPlayer;
    Animation animation;
    ImageView imageViewDVD;
    int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        addListSongs();
        CreateMediaSong();
        animation = AnimationUtils.loadAnimation(this,R.anim.dvd_rorate);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imageViewDVD.clearAnimation();
                    btnPlay.setImageResource(R.drawable.play);
                }else{
                    mediaPlayer.start();
                    imageViewDVD.startAnimation(animation);
                    btnPlay.setImageResource(R.drawable.pause60);
                }

                SetTimeRight();
                SetTimeLeft();
            }
        });

        btnLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isLooping()){
                    mediaPlayer.setLooping(false);
                    btnLoop.setImageResource(R.drawable.reload);
                }else{
                    mediaPlayer.setLooping(true);
                    btnLoop.setImageResource(R.drawable.refresh);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position > listSongs.size()-1){
                    position = 0;
                }else{
                    position++;
                }

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }else{
                    btnPlay.setImageResource(R.drawable.pause60);
                }
                CreateMediaSong();
                mediaPlayer.start();
                imageViewDVD.startAnimation(animation);
                SetTimeRight();
                SetTimeLeft();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position < 0){
                    position = listSongs.size()-1;
                }else{
                    position--;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }else{
                    btnPlay.setImageResource(R.drawable.pause60);
                }
                CreateMediaSong();
                mediaPlayer.start();
                imageViewDVD.startAnimation(animation);
                SetTimeRight();
                SetTimeLeft();
            }
        });


        skBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void CreateMediaSong(){
        mediaPlayer = new MediaPlayer().create(MainActivity.this,listSongs.get(position).getFileMp3());
        txtTitle.setText(listSongs.get(position).getName() + " - " + listSongs.get(position).getSinger());
    }

    private void addListSongs(){
        listSongs = new ArrayList<Song>();
        listSongs.add(new Song("Bad Liar", "Imagine Dragons","Sad Song", R.raw.bad_liar));
        listSongs.add(new Song("Bước qua mùa cô đơn", "Vũ. ","Sad Song", R.raw.buoc_qua_mua_co_don));
        listSongs.add(new Song("Bước qua nhau", "Vũ.","Sad Song", R.raw.buoc_qua_nhau));
        listSongs.add(new Song("Chấm đen", "DSK","Rap", R.raw.cham_den));
        listSongs.add(new Song("Cho tôi lang thang", "Ngọt && Đen Vâu","Rap Song", R.raw.cho_toi_lang_thang));
        listSongs.add(new Song("Coconut", "NULL","Funny Song", R.raw.coconut_song));
        listSongs.add(new Song("Dancing with the ghost", "Shasa Sloan","Sad Song", R.raw.dacing_with_the_ghost));
        listSongs.add(new Song("Lạ lùng", "Vũ.","Sad Song", R.raw.la_lung));
        listSongs.add(new Song("Let her go", "Passenger","Sad Song", R.raw.let_her_go));
        listSongs.add(new Song("Love is gone", "Slander","Sad Song", R.raw.love_is_gone));
        listSongs.add(new Song("Mày đang giấu cái gì ", "Đen Vâu","Rap", R.raw.may_dang_giau_cai_gi));
        listSongs.add(new Song("Nàng Thơ", "Hoàng Dũng","Sad Song", R.raw.nang_tho));
        listSongs.add(new Song("Nép vào anh và nghe anh hát", "Hoàng Dũng","Sad Song", R.raw.nep_vao_anh_va_nghe_anh_hat));
        listSongs.add(new Song("SoFar", "Binz","Rap Song", R.raw.sofar));
        listSongs.add(new Song("Some one you loved", "Lewis Capaldi","Sad Song", R.raw.some_one_you_loved));
        listSongs.add(new Song("Vì anh vẫn ", "Hoàng Dũng ","Sad Song", R.raw.vi_anh_van));
        listSongs.add(new Song("We don't talk any more", "Charlie Puth","Sad Song", R.raw.we_dont_talk_any_more));
        listSongs.add(new Song("Yếu đuối", "Hoàng Dũng","Sad Song", R.raw.yeu_duoi));
        listSongs.add(new Song("You broke me first", "Conor Maynard","Sad Song", R.raw.you_broke_me_first));
    }

    private void SetTimeRight(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        txtTimeSongRight.setText(timeFormat.format(mediaPlayer.getDuration()));
        skBarSong.setMax(mediaPlayer.getDuration());
    }

    private void SetTimeLeft(){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSongLeft.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                    skBarSong.setProgress(mediaPlayer.getCurrentPosition());

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if(position > listSongs.size()-1){
                                position = 0;
                            }else{
                                position++;
                            }

                            if(mediaPlayer.isPlaying()){
                                mediaPlayer.stop();
                            }else{
                                btnPlay.setImageResource(R.drawable.pause60);
                            }
                            CreateMediaSong();
                            mediaPlayer.start();
                            SetTimeRight();
                            SetTimeLeft();
                        }
                    });
                    handler.postDelayed(this,500);
                }
            },100);

    }

    private void Mapping(){
        txtTimeSongLeft = (TextView) findViewById(R.id.TextViewTimeLeft);
        txtTimeSongRight = (TextView) findViewById(R.id.TextViewTimeRight);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        skBarSong = (SeekBar) findViewById(R.id.seekbarTime);
        btnNext = (ImageButton) findViewById(R.id.nextbtn);
        btnPrev = (ImageButton) findViewById(R.id.prevbtn);
        btnPlay = (ImageButton) findViewById(R.id.playbtn);
        btnLoop = (ImageButton) findViewById(R.id.btnloop);
        imageViewDVD = (ImageView) findViewById(R.id.imageViewDvd);
    }
}