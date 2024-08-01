package manitsche.projeto.exercciogorjetav2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button bCalcular;
    EditText eValorTotalConta;
    SeekBar sNumeroPessoas, sPorcentagemGorjeta;
    TextView tRespostaNumeroPessoas, tRespostaPorcentagemGorjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eValorTotalConta = findViewById(R.id.editText_valor_da_conta);
        sNumeroPessoas = findViewById(R.id.seekBar_numero_de_pessoas);
        sPorcentagemGorjeta = findViewById(R.id.seekBar_porcentagem_da_gorjeta);
        tRespostaNumeroPessoas = findViewById(R.id.textViewRespostaNumeroPessoas);
        tRespostaPorcentagemGorjeta = findViewById(R.id.textViewRespostaPorcentagemGorjeta);
        bCalcular = findViewById(R.id.button_calcular);

        // Listener para atualizar o número de pessoas
        sNumeroPessoas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tRespostaNumeroPessoas.setText("Número de pessoas: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Listener para atualizar a porcentagem de gorjeta
        sPorcentagemGorjeta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tRespostaPorcentagemGorjeta.setText("Gorjeta: " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        bCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularValor();
            }
        });
    }

    private void calcularValor() {
        String valorTotalContaString = eValorTotalConta.getText().toString();

        if (valorTotalContaString.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha o valor da conta.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double valorConta = Double.parseDouble(valorTotalContaString);
            int numeroPessoas = sNumeroPessoas.getProgress();
            int porcentagemGorgeta = sPorcentagemGorjeta.getProgress();

            if (numeroPessoas == 0) {
                Toast.makeText(this, "Número de pessoas deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                return;
            }

            double valorTotalComGorgeta = valorConta + (valorConta * (porcentagemGorgeta / 100.0));
            double valorPorPessoaComGorgeta = valorTotalComGorgeta / numeroPessoas;
            double valorPorPessoaSemGorgeta = valorConta / numeroPessoas;

            abrirDialog("Você gostaria de saber o valor por pessoa com ou sem gorjeta?", valorPorPessoaComGorgeta, valorPorPessoaSemGorgeta);

        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Erro ao converter valores. Verifique o formato.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirDialog(String mensagem, double valorComGorjeta, double valorSemGorjeta) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle("Atenção");
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.stat_sys_warning);

        dialog.setPositiveButton("Com Gorjeta", (dialogInterface, which) -> {
            Toast.makeText(getApplicationContext(),
                    String.format("Valor por pessoa com gorjeta: R$ %.2f", valorComGorjeta),
                    Toast.LENGTH_LONG).show();
        });

        dialog.setNegativeButton("Sem Gorjeta", (dialogInterface, which) -> {
            Toast.makeText(getApplicationContext(),
                    String.format("Valor por pessoa sem gorjeta: R$ %.2f", valorSemGorjeta),
                    Toast.LENGTH_LONG).show();
        });

        dialog.create();
        dialog.show();
    }
}