package com.example.library_zxing;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.*;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    public final static int QRcodeWidth = 500;

    private ImageView imageView;
    private Button button;
    private EditText editText;
    private String editTextValue;
    private Thread thread;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView)findViewById(R.id.imageView);
        this.editText = (EditText)findViewById(R.id.editText);
        this.button = (Button)findViewById(R.id.button);

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextValue = editText.getText().toString();

                try {
                    bitmap = textToImageEncode(editTextValue);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth,
                    QRcodeWidth,
                    null
            );
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int i = 0; i < bitMatrixHeight; i++) {
            int offset = i * bitMatrixWidth;
            for (int j = 0; j < bitMatrixWidth; j++) {
                pixels[offset + j] = bitMatrix.get(j, i)
                        ? getResources().getColor(R.color.codeBlackColor)
                        : getResources().getColor(R.color.codeWhiteColor);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

}