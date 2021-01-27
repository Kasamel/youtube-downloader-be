package ru.kasamael.service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.parser.DefaultParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class YoutubeDownloaderService {

    private final YoutubeDownloader DOWNLOADER = new YoutubeDownloader(new DefaultParser());
    private final String DEFAULT_DIRECTORY = "dir";

    /**
     * Устанавливаем значения по умолчания
     */
    public void init() {
        DOWNLOADER.setParserRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        DOWNLOADER.setParserRetryOnFailure(1);
    }

    /**
     * Скачиваем аудио файл с сайта и возврщаем ссылку на файл
     */
    public File getAudioFileFromVideo(String videoId) throws YoutubeException, IOException {
        YoutubeVideo video = DOWNLOADER.getVideo(videoId);
        List<AudioFormat> audioFormats = video.audioFormats();
        return video.download(audioFormats.get(0), new File(DEFAULT_DIRECTORY));
    }

    public String getDefaultDirectory() {
        return DEFAULT_DIRECTORY;
    }
}
