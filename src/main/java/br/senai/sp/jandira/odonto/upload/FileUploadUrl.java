package br.senai.sp.jandira.odonto.upload;

public class FileUploadUrl {
    
    private String url;

    public FileUploadUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "FileUploadUrl [url=" + url + "]";
    }
}