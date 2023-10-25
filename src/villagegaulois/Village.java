package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;


public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	
	
	private static class Marche {
		private Etal[] etals;
		
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			
			
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		
		private int trouverEtalsOccupes() {
			int nbEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					nbEtals++;
				}
			}
			return nbEtals;
		}
		

		
		private Etal[] trouverEtals(String produit) {
			
			Etal[] etalsProduits = new Etal[trouverEtalsOccupes()-1];
			int j=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() &&  (etals[i].contientProduit(produit))) {
					etalsProduits[j] = etals[i];
					j++;
					
				}
			}
			return etalsProduits;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			String chaine = "";
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine += etals[i].afficherEtal();
				}
			}
			
			chaine += "Il reste " + (etals.length - trouverEtalsOccupes()) + " etals non utilises dans le marche.\n";
			return chaine;
		}
		
	}

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws Exception {
		
		if (chef == null) {
			throw new Exception("aucun chef dans le village");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int indiceEtal = marche.trouverEtalLibre();
		if (indiceEtal == -1) {
			chaine.append("Il n'y a plus d'etals libres...");
		} else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\nLe vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal numero " + (indiceEtal + 1) + ".\n\n");
		
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit (String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeursProduits = marche.trouverEtals(produit);
		if (etalsVendeursProduits[0] != null) {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for (int i = 0; i < etalsVendeursProduits.length; i++) {
				if (etalsVendeursProduits[i] != null) {
					chaine.append("- " + etalsVendeursProduits[i].getVendeur().getNom() + "\n");
				}
			}
		} else {
			chaine.append("Aucun vendeur ne vend des " + produit + ".\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		if (marche.trouverEtalsOccupes() == 0) {
			return "Le marche du village \"" + nom + "\" possede aucun etal";
		}
		
		return "Le marche du village \"" + nom + "\" possede plusieurs etals :\n" + marche.afficherMarche();
	}
	
	public static void main(String[] args) {
		
		Marche marche = new Marche(2);
		System.out.println(marche.afficherMarche());
		
		Gaulois asterix = new Gaulois("Asterix", 5);
		Gaulois obelix = new Gaulois("Obelix", 5);
		marche.utiliserEtal(1, asterix, "fleurs", 20);		
		marche.utiliserEtal(0, obelix, "bonbons", 40);
		Gaulois david = new Gaulois("David", 5);

		
		System.out.println(marche.afficherMarche());
		System.out.println(marche.trouverVendeur(asterix).afficherEtal());
		
		
	}
	
}
















