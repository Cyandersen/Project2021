package application;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sample")

public class SampleListWrapper 
{
	private List<Sample> sample;
	@XmlElement(name="etudiant")
	public List<Sample> getEtudiants()
	{
		return sample;
	}
	public void setEtudiants(List<Sample> etudiants)
	{
		this.sample=etudiants;
	}	
}