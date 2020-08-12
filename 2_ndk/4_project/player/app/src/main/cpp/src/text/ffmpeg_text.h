//
// Created by admin on 2020/8/12.
//

#ifndef FFMPEG_FFMPEG_TEXT_H
#define FFMPEG_FFMPEG_TEXT_H


#include <string>

class FFMpegText {

public:
    FFMpegText();


    void decodeAudio(std::string input, std::string output);

};


#endif //FFMPEG_FFMPEG_TEXT_H
