package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbetals) {
			etals = new Etal[nbetals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal > etals.length && indiceEtal < 0) {
				System.out.println("Indice incorrect");
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);

		}
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length ; i++) {
				if (etals[i]== null) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit){
			int j = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					j++;
				}
			}
			Etal[] etalproduit = new Etal[j];
			int v = 0;
			for (int i = 0; i < j; i++) {
				if (etals[i].contientProduit(produit)) {
					etalproduit[v] = etals[i];
					v++;
				}
			}
			return etalproduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
	
		private void afficherMarche() {
			int v = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
				else {
					v++;
				}
			}
			System.out.println("Il reste " + v + " �tals non utilis�s dans le march�.\n");
		}
		

	}

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();

	}
	


}