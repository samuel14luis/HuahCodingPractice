package cenadenavidad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam
 *
 * Un pequeño Walter tenía examen de Matemática III sobre el tema de Ecuaciones
 * diferenciales, eran los finales, pero justo ese mismo día era la cena
 * navideña donde iban a dar medio pollo a la brasa en el comedor de la
 * faustino. El examen de Matemática III consistía de "n" preguntas ordenados
 * por dificultad, el demora 5*i minutos en resolver el i-th problema. El
 * comedor cierra a las 6pm y su examen empieza a las 2pm. Y el sabe que demora
 * "k" minutos en llegar de su salón al comedor. La pregunta es,
 *
 * ¿Cuantos problemas como máximo puede resolver si el quiere llegar a comer su
 * rico medio pollo a la brasa?
 *
 * Entrada: La entrada consiste en dos números, el número "n" que es la cantidad
 * de problemas planteados y el número "k" que es la cantidad de minutos que
 * demora de su salón al comedor.
 *
 * Salida: El número máximo de problemas que puede resolver.
 *
 *
 * INPUT: OUTPUT: 2 2 3 222 4 4 190
 *
 */
public class CenaDeNavidad {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        //CALCULATING AVAILABLE TIME
        int availableTime = getDifMinutesBetweenDates("2022-03-15 14:00", "2022-03-15 18:00");
        System.out.println(availableTime);

        //READING A FILE
        File file = new File("C:\\Users\\Sam\\Documents\\HuahCoding\\HuahCodingPractice\\CenaDeNavidad\\src\\cenadenavidad\\input\\Apura a la cena de navidad!!_in.txt");
        Scanner scan = new Scanner(file);

        //READING N
        int n = Integer.parseInt(scan.nextLine());
        System.out.println("Filas: " + n);

        //MAKING OUTPUT FILE
        File output_path = new File("C:\\Users\\Sam\\Documents\\HuahCoding\\HuahCodingPractice\\CenaDeNavidad\\src\\cenadenavidad\\output\\output.txt");
        cleanFile(output_path);

        //SETTING UP
        String[] aux;
        int n_problemas;
        int k_minutos;

        //SOLVER
        for (int i = 1; i <= n; i++) {
            aux = scan.nextLine().split(" ");
            n_problemas = Integer.parseInt(aux[0]);
            k_minutos = Integer.parseInt(aux[1]);
            wr(output_path.getAbsolutePath(), solver(n_problemas, k_minutos, availableTime) + NEW_LINE);
        }

    }

    public static int solver(int n_problemas, int k_minutos, int availableTime) {
        int carry = 0;
        int num = 0;

        for (int i = 1; i <= n_problemas; i++) {
            carry += i * 5;
            System.out.println("i:" + i + " carry -> " + carry);
            if (carry <= availableTime - k_minutos) {
                num = i;
            } else {
                break;
            }
        }

        return num;
    }

    private static final String NEW_LINE = System.lineSeparator();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm", Locale.US);
    public static SimpleDateFormat sdh = new SimpleDateFormat("h", Locale.US);
    public static SimpleDateFormat sdm = new SimpleDateFormat("m", Locale.US);

    public static Date getDifferenceBetwenDates(Date dateInicio, Date dateFinal) {
        long milliseconds = dateFinal.getTime() - dateInicio.getTime();
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, seconds);
        c.set(Calendar.MINUTE, minutes);
        c.set(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public static int getDifMinutesBetweenDates(String date1, String date2) {
        int minutes = 0;
        int hours;
        try {
            Date diff = getDifferenceBetwenDates(sdf.parse(date1), sdf.parse(date2));
            minutes = Integer.parseInt(sdm.format(diff));
            hours = Integer.parseInt(sdh.format(diff));
            minutes = minutes + hours * 60;
        } catch (ParseException ex) {
            System.out.println(ex);
        }

        return minutes;
    }

    private static void wr(String path, String content) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);

            pw.print(content);
            pw.flush();

        } catch (IOException ex) {
            Logger.getLogger(CenaDeNavidad.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(pw != null) pw.close();
                if(bw != null) bw.close();
                if(fw != null) fw.close();
            } catch (IOException io) {
                Logger.getLogger(CenaDeNavidad.class.getName()).log(Level.SEVERE, null, io);
            }

        }

    }

    private static void cleanFile(File output_path) {
        
        PrintWriter writer; 
        try {
            writer = new PrintWriter(output_path);
            writer.print(""); 
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CenaDeNavidad.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

}
