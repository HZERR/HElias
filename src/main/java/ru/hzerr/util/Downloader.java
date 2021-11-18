package ru.hzerr.util;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import ru.hzerr.exception.network.DownloadException;
import ru.hzerr.exception.network.WebSiteNotWorkingException;
import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HFile;
import ru.hzerr.log.SessionLogManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {

    public static void download(@NotNull String url, @NotNull HFile destination) throws IOException, WebSiteNotWorkingException {
        destination.create();
        final URL downloadUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            SessionLogManager.getManager().getLogger().info("Host|Port - " + downloadUrl.getHost() + ":" + downloadUrl.getPort() + "\n\tRequest - " + url);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            try (InputStream is = connection.getInputStream(); OutputStream os = destination.openOutputStream()) {
                IOUtils.copy(is, os);
            }
        } else throw new WebSiteNotWorkingException(url, responseCode);
        connection.disconnect();
    }

    public static BaseFile download(@NotNull String url, @NotNull BaseDirectory whereToDownload) throws IOException, DownloadException, WebSiteNotWorkingException {
        whereToDownload.create();
        BaseFile destination;
        final URL downloadUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fieldValue = connection.getHeaderField("Content-Disposition");
            if (fieldValue == null || ! fieldValue.contains("filename=\"")) {
                throw new DownloadException("Unable to get a filename from the server");
            }
            String filename = fieldValue.substring(fieldValue.indexOf("filename=\"") + 10, fieldValue.length() - 1);
            destination = new HFile(whereToDownload, filename);
            SessionLogManager.getManager().getLogger().info("Host|Port - " + downloadUrl.getHost() + ":" + downloadUrl.getPort() + "\n\tRequest - " + url);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            try (InputStream is = connection.getInputStream(); OutputStream os = destination.openOutputStream()) {
                IOUtils.copy(is, os);
            }
        } else throw new WebSiteNotWorkingException(url, responseCode);
        connection.disconnect();
        return destination;
    }
}
