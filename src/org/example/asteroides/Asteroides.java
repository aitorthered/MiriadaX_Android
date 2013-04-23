package org.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends Activity {

	private static final int GAME_ACTIVITY = 31415;
	public static AlmacenPuntuaciones almacen; // = new AlmacenPuntuacionesArray();
	//	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button bSalir =(Button) findViewById(R.id.Button04);
		bSalir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPuntuaciones(view);
			}
		});

		bSalir =(Button) findViewById(R.id.Button03);
		bSalir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarAcercaDe(null);
			}
		});
		bSalir =(Button) findViewById(R.id.Button02);
		bSalir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarPreferencias(null);
			}
		});
		bSalir =(Button) findViewById(R.id.Button01);
		bSalir.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarJuego(null);
			}
		});

		//		mp = MediaPlayer.create(this, R.raw.audio);
		startService(new Intent(Asteroides.this,
				ServicioMusica.class));
		//		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		//		String puntuacion = sharedPref.getString("puntuacion", "0");
		//		if(puntuacion.equals("1")){ // 
		//			almacen = new AlmacenPuntuacionesPreferencias(this);
		//		}
		//		else if(puntuacion.equals("2")){ // 
		//			almacen = new AlmacenPuntuacionesFicheroInterno(this);
		//
		//		}
		//		else{
		//			almacen = new AlmacenPuntuacionesArray();
		//		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String puntuacion = sharedPref.getString("puntuacion", "0");
		if(puntuacion.equals("1")){ // 
			almacen = new AlmacenPuntuacionesPreferencias(this);
		}
		else if(puntuacion.equals("2")){ // 
			almacen = new AlmacenPuntuacionesFicheroInterno(this);

		}
		else{
			almacen = new AlmacenPuntuacionesArray();
		}

		//		mp.start();
	}
	@Override
	protected void onStop() {
		super.onStop();
		//		mp.pause();
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		stopService(new Intent(Asteroides.this,
				ServicioMusica.class));
	}

	@Override
	protected void onSaveInstanceState(Bundle saveInstance){
		super.onSaveInstanceState(saveInstance);
		//		if (mp != null) {
		//			int pos = mp.getCurrentPosition();
		//			saveInstance.putInt("posicionSonido", pos);
		//		}
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstance){
		super.onRestoreInstanceState(savedInstance);
		//		if (savedInstance != null && mp != null) {
		//			int pos = savedInstance.getInt("posicionSonido");
		//			mp.seekTo(pos);
		//		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.acercaDe:
			lanzarAcercaDe(null);
			break;
		case R.id.config:
			lanzarPreferencias(null);
			break;
		}
		return true; /** true -> consumimos el item, no se propaga*/
	}

	public void lanzarAcercaDe(View view){
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}
	public void lanzarPreferencias(View view){
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void lanzarPuntuaciones(View view) {

		Intent i = new Intent(this, Puntuaciones.class);

		startActivity(i);

	}

	private void lanzarJuego(View view) {
		Intent i = new Intent(getBaseContext(), Juego.class);
		startActivityForResult(i, GAME_ACTIVITY);
	}

	@Override 
	protected void onActivityResult (int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==GAME_ACTIVITY & resultCode==RESULT_OK & data!=null) {
			int puntuacion = data.getExtras().getInt("puntuacion");
			String nombre = "Yo";
			// Mejor leerlo desde un Dialog o una nueva actividad
			//AlertDialog.Builder
			almacen.guardarPuntuacion(puntuacion, nombre,
					System.currentTimeMillis());
			lanzarPuntuaciones(null);
		}
	}
}
