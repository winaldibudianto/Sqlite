package com.example.library_itextg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";
    private EditText contentEditText;
    private Button createButton;
    private File pdfFile;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.contentEditText = (EditText)findViewById(R.id.edit_text_content);
        this.createButton = (Button)findViewById(R.id.button_create);
        this.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentEditText.getText().toString().isEmpty()) {
                    contentEditText.setError("Isi tulisan terlebih dahulu!");
                    contentEditText.requestFocus();
                    return;
                }

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriterStoragePermission = ActivityCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(hasWriterStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOkCancel(
                            "You need to allow access to storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        }, REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException | DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {
        File docsFolder = new File(
                Environment.getExternalStorageDirectory() + "/Documents"
        );

        if(!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("TAG", "Buat Folder PDF Baru");
        }

        this.pdfFile = new File(docsFolder.getAbsoluteFile(), "PDFGenerate.pdf");
        OutputStream output = new FileOutputStream(this.pdfFile);
        Document document = new Document();

        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(this.contentEditText.getText().toString()));
        document.close();

        this.previewPdf();
    }

    private void previewPdf() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if(list.size() > 0) {
            Intent pdfIntent = new Intent();
            pdfIntent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(this.pdfFile);
            pdfIntent.setDataAndType(uri, "application/pdf");

            startActivity(pdfIntent);
        } else {
            Toast.makeText(
                    this,
                    "Download aplikasi pdf viewer untuk melihat hasil generate",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}