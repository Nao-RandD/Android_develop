package com.naorandd.testservicenotice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.IOException

class SoundManageService : Service() {

    private var _player: MediaPlayer? = null

    override fun onCreate() {
        _player = MediaPlayer()

        val id = "soundmanagerservice_notification_channel"
        val name = getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //音声ファイルのURI文字列を作成。
        val mediaFileUriStr = "android.resource://${packageName}/${R.raw.mountain_stream}"
        //音声ファイルのURI文字列を元にURIオブジェクトを生成。
        val mediaFileUri = Uri.parse(mediaFileUriStr)
        try {
            //メディアプレーヤーに音声ファイルを指定。
            _player?.setDataSource(applicationContext, mediaFileUri)
            //非同期でのメディア再生準備が完了した際のリスナを設定。
            _player?.setOnPreparedListener(PlayerPreparedListener())
            //メディア再生が終了した際のリスナを設定。
            _player?.setOnCompletionListener(PlayerCompletionListener())
            //非同期でメディア再生を準備。
            _player?.prepareAsync()
        }
        catch(ex: IllegalArgumentException) {
            Log.e("ServiceSample", "メディアプレーヤー準備時の例外発生", ex)
        }
        catch(ex: IOException) {
            Log.e("ServiceSample", "メディアプレーヤー準備時の例外発生", ex)
        }

        //定数を返す。
        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        //フィールドのプレーヤーがnullじゃなかったら
        _player?.let {
            //プレーヤーが再生中なら…
            if(it.isPlaying) {
                //プレーヤーを停止。
                it.stop()
            }
            //プレーヤーを解放。
            it.release()
            //プレーヤー用フィールドをnullに。
            _player = null
        }
    }

    private inner class PlayerPreparedListener : MediaPlayer.OnPreparedListener{
        override fun onPrepared(mp: MediaPlayer) {
            mp.start()

            //Notificationを作成するBuilderクラス生成。
            val builder = NotificationCompat.Builder(applicationContext,
                "soundmanagerservice_notification_channel")
            //通知エリアに表示されるアイコンを設定。
            builder.setSmallIcon(android.R.drawable.ic_dialog_info)
            //通知ドロワーでの表示タイトルを設定。
            builder.setContentTitle(getString(R.string.msg_notification_title_start))
            //通知ドロワーでの表示メッセージを設定。
            builder.setContentText(getString(R.string.msg_notification_text_start))

            //起動先Activityクラスを指定したIntentオブジェクトを生成。
            val intent = Intent(applicationContext, MainActivity::class.java)
            //起動先アクティビティに引き継ぎデータを格納。
            intent.putExtra("fromNotification", true)
            //PendingIntentオブジェクトを取得。
            val stopServiceIntent = PendingIntent.getActivity(applicationContext,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
            //PendingIntentオブジェクトをビルダーに設定。
            builder.setContentIntent(stopServiceIntent)
            //タップされた通知メッセージを自動的に消去するように設定。
            builder.setAutoCancel(true)

            //BuilderからNotificationオブジェクトを生成。
            val notification = builder.build()
            //NotificationManagerオブジェクトを取得。
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //通知。
            manager.notify(1, notification)
        }
    }

    private inner class PlayerCompletionListener : MediaPlayer.OnCompletionListener{
        override fun onCompletion(mp: MediaPlayer) {
            val builder = NotificationCompat.Builder(applicationContext,
                "soundmanagerservice_notification_channel")
            builder.setSmallIcon(android.R.drawable.ic_dialog_info)
            builder.setContentTitle(getString(R.string.msg_notification_title_finish))
            builder.setContentText(getString(R.string.msg_notification_text_finish))

            val notification = builder.build()
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.notify(0, notification)
            stopSelf()
        }
    }
}
