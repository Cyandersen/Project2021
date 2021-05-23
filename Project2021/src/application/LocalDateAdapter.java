/*
* Author: cy.andersen
* Date: May 7, 2021
* Description:
* Warpper pour le SampleController
*/
package application;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;
/*
 * Ce fichier permet de gérer la conversion de de la date
 * locale du programme java vers le fichier XML (marshall)
 * et l'inverse (unmarshall)
 * 
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}