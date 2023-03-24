package juegoAhorcado;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class JuegoAhorcado {
	public static void main(String[] args)throws FileNotFoundException {
    	int arrayPuntuaciones[]= new int [6];
    	Scanner entrada = new Scanner (System.in);
    	String opcion = "";
    	boolean elegirNivel = false;
    	int partidasJugadas = 0;
     /** BUCLE QUE CONTROLA QUE EL USUARIO ELIJA UNA OPCIÓN CORRECTA */
    	do {
    		System.out.println("************************");
    		System.out.println("SELECCIONA UNA OPCIÓN:\n     -ELEGIR NIVEL\n     -AYUDA");
            opcion=entrada.nextLine();
            if (opcion.equalsIgnoreCase("ELEGIR NIVEL")) {
            	gestionNivelYPuntos(arrayPuntuaciones, partidasJugadas);// LLAMO AL MENÚ
            	elegirNivel = true;
            } else if (opcion.equalsIgnoreCase("AYUDA")) {
                System.out.println("EL JUEGO DEL AHORCADO:\n     -INTENTA ADIVINAR LA PALABRA.\n     -ESCRIBE LETRAS Y SI ESTÁN EN LA PALABRA, SE RELLENARÁN LO HUECOS EN BLANCO.\n     -TIENES HASTA 6 FALLOS PARA CONSEGUIRLO.");
            } else {
                System.err.println("ERROR, DEBE SELECCIONAR UNA DE LAS DOS OPCIONES DISPONIBLES");
            }
        } while (!elegirNivel);
     /** FIN BUCLE OPCIÓN CORRECTA */
    }
/****************************************************** FIN MAIN */    	
    //SELECCIONAR NIVEL Y ALMACENAR PUNTOS DE LAS PARTIDAS
    public static void gestionNivelYPuntos(int arrayPuntuaciones[], int partidasJugadas) throws FileNotFoundException{
        Scanner entrada = new Scanner (System.in);
        String opcion = "", nivel = "";
       boolean error = false; boolean partidaGanada = false;
    /** BUCLE QUE CONTROLA QUE EL USUARIO ELIJA UNO DE LOS TRES NIVELES */
       			do {
            		error = false;
            		System.out.println("************************");
            		System.out.println("SELECCIONA UN NIVEL:\n     -FÁCIL\n     -MEDIO\n     -DIFICIL\n     -EXTREMO");
            		nivel = entrada.nextLine();
	                if ((!nivel.equalsIgnoreCase("FACIL"))&&(!nivel.equalsIgnoreCase("MEDIO"))&&(!nivel.equalsIgnoreCase("DIFICIL")&&(!nivel.equalsIgnoreCase("EXTREMO")))) {
	    	            System.err.println("ERROR, ELIGE UNO DE LOS TRES NIVELES DISPONIBLES");
	    	            error = true;
	                }	
                }while(error);
       	/** FIN BUCLE NIVELES */
                partidaGanada = generarPalabra(nivel,arrayPuntuaciones, partidasJugadas); //LLAMO A GENERAR PALABRA
                if (nivel.equalsIgnoreCase("FACIL")){ //VOY ALMACENANDO LAS PARTIDAS
                	if (partidaGanada) {
                    arrayPuntuaciones[0] += 1;
                } else {
                    arrayPuntuaciones[1] += 1;
                }
            } else if (nivel.equalsIgnoreCase("MEDIO")) {
                if (partidaGanada) {
                    arrayPuntuaciones[2] += 1;
                } else {
                    arrayPuntuaciones[3] += 1;
                }
            } else if (nivel.equalsIgnoreCase("DIFICIL")){
                if (partidaGanada) {
                    arrayPuntuaciones[4] += 1;
                } else {
                    arrayPuntuaciones[5] += 1;
                }
            }
/******************************************************* MODIFICACIÓN APARTADO A *************************************************/
            System.out.println("************************");
            System.out.println("PARTIDAS JUGADAS HASTA AHORA: "+ (partidasJugadas += 1));
    	jugarOtraVez(arrayPuntuaciones, partidasJugadas);//LAMO A JUGAR OTRA VEZ
        entrada.close();
    }
/****************************************************** FIN MÉTODO GESTION NIVEL Y PUNTOS */    	
    //LEER EL FICHERO SEGÚN EL NIVEL ELEGIDO
    public static boolean generarPalabra(String nivel,int arrayPuntuaciones[], int partidasJugadas) throws FileNotFoundException{
        int lineasFichero = 0, indiceAleatorio = 0;
        String fichero = "";
        if (nivel.equalsIgnoreCase("FACIL")){
            fichero = "src/juegoAhorcado/nivelFacil";
        } else if (nivel.equalsIgnoreCase("MEDIO")){
            fichero = "src/juegoAhorcado/nivelMedio";
        } else if (nivel.equalsIgnoreCase("DIFICIL")){
            fichero = "src/juegoAhorcado/nivelDificil";
        } else {
/******************************************************* MODIFICACIÓN APARTADO B ********************************************************/
        	System.out.println("************************");
        	System.out.println("NIVEL DE JUEGO EXTREMO EN FASE DE PRUEBAS");
        	System.out.println("************************");
        	System.out.println("PARTIDAS JUGADAS HASTA AHORA:"+(partidasJugadas += 1)); 
        	gestionNivelYPuntos(arrayPuntuaciones, partidasJugadas);
        }
        File archivo = new File(fichero);
        Scanner entrada = new Scanner (archivo);
        while (entrada.hasNextLine()) { //CUENTO CUÁNTAS LÍNEAS TIENE EL FICHERO
            entrada.nextLine();
            lineasFichero++;
        }
        entrada.close(); //CIERRO EL SCANNER
        String arrayPalabras[] = new String [lineasFichero]; //CREO UN ARRAY PARA METER EL FICHERO DEL NIVEL
        entrada = new Scanner (archivo); //HAY QUE REINCIAR EL SCANNER PARA EXTRAER EL CONTENIDO
        for (int i = 0;i < arrayPalabras.length;i++) {
            arrayPalabras[i] = entrada.nextLine();
        }
        indiceAleatorio = (int) (Math.random()*(lineasFichero-1)+1); //GENERO UN NÚMERO ALEATORIO DENTRO DE LAS DIMENSIONES DEL FICHERO
        String huecos[] = new String [arrayPalabras[indiceAleatorio].length()]; //ARRAY CON LA LONGITUD DE LA PALABRA ALEATORIA ELEGIDA

        huecos = pintarPalabra(arrayPalabras[indiceAleatorio]); //LLAMO A pintarPalabra() PARA PINTAR LOS HUECOS DE LA PALABRA
        boolean partidaGanada = juegoPedirLetras(huecos, arrayPalabras[indiceAleatorio]); //LLAMO A proponerLetras() PARA EMPEZAR EL JUEGO DE MANERA INTERACTIVA
        return partidaGanada;
    }
/****************************************************** FIN MÉTODO GENERAR PALABRA */    	
    public static String[] pintarPalabra(String palabra) {
        String huecos[] = new String [palabra.length()];
        Arrays.fill(huecos,"_ ");
        for(int i = 0 ; i < huecos.length; i++){
            System.out.print(huecos[i]);
        }
        System.out.println();
        return huecos;
    }
/****************************************************** FIN MÉTODO PINTAR PALABRA */ 
    public static boolean juegoPedirLetras(String huecos[], String palabra) throws FileNotFoundException {
        Scanner entrada = new Scanner (System.in); 
        int fallos = 0; String opcion = "";
        boolean completa = false;
        boolean partidaGanada = false; 
  /** BUCLE LIMITE DE FALLOS Y PALABRA COMPLETA */
        do {
            int cont = 0;
            System.out.println("INTRODUCE UNA LETRA");
            String letra = entrada.next();
           /** BUCLE QUE CONTROLA QUE EL USUARIO INTRODUZCA UNA LETRA VÁLIDA */
/******************************************************* MODIFICACIÓN APARTADO C ********************************************************/
            while (!letra.toLowerCase().matches("[a-z]")&&(!letra.toLowerCase().matches("ñ")&&(!letra.toLowerCase().matches("[0-9]")))){
            	System.err.println("ERROR, LA LETRA DEBE ESTAR ENTRE LA A Y LA Z");
            	System.out.println("INTRODUCE UNA LETRA");
                letra = entrada.next();
            }
           /** FIN BUCLE LETRA VALIDA */
            char[] arrayPalabra = palabra.toLowerCase().toCharArray(); // CONVIERTO LA PALABRA A UN ARRAY DE CHAR
            boolean letraEncontrada = false; //VARIABLE PARA SABER SI LA LETRA EXISTE EN LA PALABRA
         /** BUCLE PARA BUSCAR Y SUSTITUIR LETRAS ENCONTRADAS EN HUECOS[] */
            for (int i = 0; i < arrayPalabra.length; i++){
                if (arrayPalabra[i] == letra.toLowerCase().charAt(0)){
                    huecos[i] = letra;
                    letraEncontrada = true;
                }
            }
            if (!letraEncontrada){
                fallos++; //CONTADOR DE FALLOS SI LA LETRA NO ESIXTE EN LA PALABRA
                numFallos(fallos);
            }
         /** FIN BUCLE LETRA ENCONTRADA */

         /** BUCLE PARA COMPROBAR SI LA PALABRA YA ESTÁ COMPLETA */
            for (int j = 0; j < huecos.length; j++){
                if (huecos[j].charAt(0) != '_'){ //COMPRUEBO SI EN CADA POSICIÓN DE LA PALABRA HAY UNA LETRA O UN HUECO
                    cont++;
                }
            }
            if (cont == huecos.length){
            	completa = true;
                partidaGanada = true;
            }
          /** FIN BUCLE PALABRA COMPLETA */

          /** BUCLE MOSTRAR LA PALABRA Y LOS HUECOS */
            for(int i = 0 ; i < huecos.length; i++){
                System.out.print(huecos[i].toLowerCase());
            }
          /** FIN BUCLE MOSTRAR PALABRA */
            System.out.println();
        } while ((fallos<6)&&(!completa)); 
  /** FIN BUCLE FALLOS Y PALABRA COMPLETA */
		System.out.println("************************");
        if (fallos == 6) {
        	System.out.println("LO SIENTO, HAS PERDIDO LA PARTIDA. LA PALABRA ERA: "+ palabra.toUpperCase());
        } else {
        	System.out.println("¡ENHORABUENA, HAS GANADO LA PARTIDA!");
        }
        return partidaGanada;
    }
/****************************************************** FIN MÉTODO JUEGO PEDIR LETRAS */    
   public static void jugarOtraVez (int arrayPuntuaciones[], int partidasJugadas) throws FileNotFoundException {
	   Scanner entrada = new Scanner (System.in);
	   String partida = "";
	   boolean error = false;
	 /** BUCLE QUE CONTROLA QUE EL USUARIO ELIJA UNA DE LAS DOS OPCIONES */
	   do {
		   error = false;
		   System.out.println("************************");
		   System.out.println("FIN DE LA PARTIDA\nSELECCIONE UNA OPCIÓN:\n     -VOLVER A JUGAR\n     -SALIR");
	   partida = entrada.nextLine();
	    if(partida.equalsIgnoreCase("VOLVER A JUGAR")) {
	    	gestionNivelYPuntos(arrayPuntuaciones, partidasJugadas); //LLAMO A menu()
	    } else if (partida.equalsIgnoreCase("SALIR")) {
	    	puntuaciones(arrayPuntuaciones); //LLAMO A puntuaciones()
	    } else {
	    	System.err.println("ERROR, DEBE ELEGIR UNA DE LAS DOS OPCIONES");
	    	error = true;
	    }
	   }while (error); 
	 /** FIN BUCLE OPCIONES */
   }
/****************************************************** FIN MÉTODO JUGAR OTRA VEZ */    
    public static void numFallos (int fallos) {
        if (fallos>0) {
            switch (fallos) {
                case 1:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                            |               \n"
                            + "      ||                                    \n"
                            + "      ||                                  \n"
                            + "      ||                                     \n"
                            + "      ||                                        \n"
                            + "      ||                                             \n"
                            + "      ||                                           \n"
                            + "      ||                                           \n"
                            + "      ||                                        \n"
                            + "  ____||____  ");
                    break;
                case 2:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                           _|_                 \n"
                            + "      ||                         _(___)_            \n"
                            + "      ||                         | - - |              \n"
                            + "      ||                         |  <  |             \n"
                            + "      ||                         |_==__|               \n"
                            + "      ||                                             \n"
                            + "      ||                                           \n"
                            + "      ||                                           \n"
                            + "      ||                                        \n"
                            + "  ____||____  ");
                    break;
                case 3:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                           _|_                 \n"
                            + "      ||                         _(___)_            \n"
                            + "      ||                         | - - |              \n"
                            + "      ||                         |  <  |             \n"
                            + "      ||                         |_==__|               \n"
                            + "      ||                            ||                 \n"
                            + "      ||                            ||                \n"
                            + "      ||                            ||                \n"
                            + "      ||                            ||             \n"
                            + "  ____||____  ");
                    break;
                case 4:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                           _|_                 \n"
                            + "      ||                         _(___)_            \n"
                            + "      ||                         | - - |              \n"
                            + "      ||                         |  <  |             \n"
                            + "      ||                         |_==__|               \n"
                            + "      ||                            ||                 \n"
                            + "      ||                            ||||                \n"
                            + "      ||                            ||  ||              \n"
                            + "      ||                            ||    ||         \n"
                            + "  ____||____  ");
                    break;
                case 5:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                           _|_                 \n"
                            + "      ||                         _(___)_            \n"
                            + "      ||                         | - - |              \n"
                            + "      ||                         |  <  |             \n"
                            + "      ||                         |_==__|               \n"
                            + "      ||                            ||                 \n"
                            + "      ||                          ||||||                \n"
                            + "      ||                        ||  ||  ||              \n"
                            + "      ||                       ||   ||    ||         \n"
                            + "  ____||____  ");
                    break;
                case 6:
                    System.out.println("      ______________________________                 \n"
                            + "      ||                            |                \n"
                            + "      ||                            |                \n"
                            + "      ||                           _|_                 \n"
                            + "      ||                         _(___)_            \n"
                            + "      ||      CRACK!!!           | - - |              \n"
                            + "      ||                         |  <  |             \n"
                            + "      ||                         |_==__|               \n"
                            + "      ||                            ||                 \n"
                            + "      ||                          ||||||                \n"
                            + "      ||                        ||  ||  ||              \n"
                            + "      ||                       ||   ||    ||         \n"
                            + "      ||                          ||  ||          \n"
                            + "      ||                         ||    ||            \n"
                            + "      ||                        ||      ||              \n"
                            + "  ____||____  ");
                    break;
            }
        }
    }
/****************************************************** FIN MÉTODO NUM FALLOS */ 
    public static void puntuaciones (int arrayPartidas[]) { //LE PASAMOS UN ARRAY CON LAS PARTIDAS GANADAS Y PERDIDAS
		System.out.println("************************");
    	System.out.println("RESUMEN DE LA PARTIDA:\n     -NIVEL FÁCIL: "+arrayPartidas[0]+" GANADAS, "+arrayPartidas[1]+" PERDIDAS\n     -NIVEL MEDIO: "+arrayPartidas[2]+" GANADAS, "+arrayPartidas[3]+" PERDIDAS\n     -NIVEL DIFICIL: "+arrayPartidas[4]+" GANADAS, "+arrayPartidas[5]+" PERDIDAS ");
    }
/****************************************************** FIN MÉTODO PUNTUACIONES */ 
}