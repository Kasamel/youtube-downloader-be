package ru.kasamael.service;

import com.github.kiulian.downloader.YoutubeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kasamael.model.dto.AudioDTO;
import ru.kasamael.model.dto.AudioDownloadRequestDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {

    private static final String DELIMITER = "-";

    private final YoutubeDownloaderService youtubeDownloaderService;

    /**
     * Получение всех записей из папки по умолчанию
     */
    public List<AudioDTO> getAllAudio() throws IOException {
        Path audioFolder = Paths.get(youtubeDownloaderService.getDefaultDirectory());
        return Files.exists(audioFolder) ? getAudioListFromFolder(audioFolder) : Collections.emptyList();
    }

    /**
     * Достаём список файлов и преобразовываем имя файла в AudioDTO
     */
    private List<AudioDTO> getAudioListFromFolder(Path audioFolder) throws IOException {
        return Files.list(audioFolder).map(file -> {
            if (file != null) {
                String[] values = file.toFile().getName().split(DELIMITER);
                return new AudioDTO(values[0], values[1]);
            }
            return new AudioDTO();
        }).collect(Collectors.toList());
    }

    /**
     * Получение аудио файла по урлу
     */
    public byte[] getAudioFile(AudioDownloadRequestDTO audioDownloadRequestDTO) throws IOException, YoutubeException {
        File audioFile = this.extractVideoIdFromUrlAndDownloadAudioFile(audioDownloadRequestDTO.getUrl());
        try (InputStream inputStream = new FileInputStream(audioFile); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buf = new byte[512];
            while (inputStream.read(buf) != -1) {
                byteArrayOutputStream.write(buf);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * Извлекаем идентификатор видео и скачиваем аудио файл
     */
    private File extractVideoIdFromUrlAndDownloadAudioFile(String url) throws IOException, YoutubeException {
        return this.downloadAudioFile(extractVideoIdFromUrl(url));
    }

    /**
     * Извлекаем идентификатор видео
     */
    private String extractVideoIdFromUrl(String url) {
        final int VIDEO_ARGUMENT_LENGTH = 2;
        final int ID_POSITION = 0;
        final String DELIMITER = "&";
        final String VIDEO_ARGUMENT_VALUE = "v";
        return url.substring(url.indexOf(VIDEO_ARGUMENT_VALUE) + VIDEO_ARGUMENT_LENGTH).split(DELIMITER)[ID_POSITION];
    }

    /**
     * Скачиваем файл с сайта
     */
    private File downloadAudioFile(String videoId) throws YoutubeException, IOException {
        youtubeDownloaderService.init();
        return youtubeDownloaderService.getAudioFileFromVideo(videoId);
    }
}
