package br.senai.sp.jandira.odonto.upload;

public class FileUpload {

    private String fileName;
    private String mimeType;
    private String base64;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "FileUpload [base64=" + base64 + ", fileName=" + fileName + ", mimeType=" + mimeType + "]";
    }
}