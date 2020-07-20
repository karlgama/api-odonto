package br.senai.sp.jandira.odonto.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.internal.FirebaseService;

import org.springframework.stereotype.Service;

// Já que anotamos que a classe é um serviço o Spring vai executar o serviço automaticamente no momento correto
@Service
public class FireBaseStorageService {

    // Avisamos ao Spring que esse método só será executado uma vez
    @PostConstruct
    private void init() throws IOException {

        if (FirebaseApp.getApps().isEmpty()) {

            InputStream serviceAccount = FirebaseService.class
                    .getResourceAsStream("/odontoapp-6f8bd-firebase-adminsdk-t18ks-d32a3223dd.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket("odontoapp-6f8bd.appspot.com").build();

            FirebaseApp.initializeApp(options);
        }
    }

    public String upload(FileUpload file) {
        Bucket bucket = StorageClient.getInstance().bucket();

        // Transformar o base64 em bytes (arquivo)
        byte[] bytes = Base64.getDecoder().decode(file.getBase64());

        // Cria o arquivo com os bytes
        Blob blob = bucket.create(file.getFileName(), bytes, file.getMimeType());

        // Configuração da permissão pra todos conseguirem ler o arquivo
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return "https://storage.googleapis.com/" + bucket.getName() + "/" + file.getFileName();
    }

    public void delete(String fileName) {
        Bucket bucket = StorageClient.getInstance().bucket();

        BlobId blobId = BlobId.of(bucket.getName(), fileName);

        bucket.getStorage().delete(blobId);
    }
}