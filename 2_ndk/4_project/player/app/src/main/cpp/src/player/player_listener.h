//
// Created by soul on 2020/6/2.
//

#ifndef FFMPEG_PLAYER_LISTENER_H
#define FFMPEG_PLAYER_LISTENER_H


class PlayerListener {

    virtual void onError(int errorCode) = 0;

    virtual void onProgress(int progress) = 0;

    virtual void onTotal(int total) = 0;

    virtual void onPrepare() = 0;
};

#endif //FFMPEG_PLAYER_LISTENER_H
