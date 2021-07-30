package exportar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class  GeneradorPDF {

    public static void generadorPDF(String contenido, String ruta){
        String[] mensajeArr = ciclarMensaje(contenido);// 4.6
        int limite = 46;
        int numPages=(int) Math.ceil(mensajeArr.length/(float)limite);
        int cantidadLineas = mensajeArr.length;
        int aux=0;

        try (PDDocument document = new PDDocument()) {// aca intenta crear el documento

            for(int i=0;i<numPages;i++) {
                PDPage page = new PDPage(PDRectangle.LETTER);//se crea una pagina y se le da el tamanio
                document.addPage(page);//se agrega la pagina al documento

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Text
                contentStream.beginText();
                contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 52);
                limite = (i==numPages-1)?cantidadLineas:46*(1+i);
                for (int j=aux; j < limite; j++) {
                    contentStream.showText(mensajeArr[j]);
                    contentStream.newLineAtOffset(0, -15);
                    if(j==limite-1){
                        aux=j+1;
                    }
                }
                //contentStream.showText(contenido);
                contentStream.endText();
                contentStream.close();
            }

            // Image
            //PDImageXObject image = PDImageXObject.createFromByteArray(document, Main.class.getResourceAsStream("/java.png").readAllBytes(), "Java Logo");
            //contentStream.drawImage(image, 20, 20, image.getWidth() / 3, image.getHeight() / 3);


            document.save(ruta);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String[] ciclarMensaje(String mensaje) {
        String[] mensajeArr;
        mensaje = mensaje.replace("\t","    ");
        return mensajeArr = mensaje.split("\n");
    }
}
