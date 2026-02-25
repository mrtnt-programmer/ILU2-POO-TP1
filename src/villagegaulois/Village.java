package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbrEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbrEtals);
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
	
	class Marche {
		Etal etals[];
		int nbrEtals;
		public Marche(int nbrEtals) {
			this.nbrEtals = nbrEtals;
			etals = new Etal[nbrEtals];
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit , int nbProduit) {
			etals[indiceEtal] = new Etal();
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre() {
			for (int i=0;i<nbrEtals;i++) {
				if (etals[i] == null || !(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}
		
		Etal[] trouverProduit(String produit) {
			Etal etalsAvecProduit[] = new Etal[this.nbrEtals];
			int nbrEtalsAvecProduit = 0;
			for (int i=0;i<nbrEtals && etals[i] != null;i++) {
				if ((etals[i].contientProduit(produit))) {
					etalsAvecProduit[nbrEtalsAvecProduit] = etals[i];
					nbrEtalsAvecProduit++;
				}
			}
			return etalsAvecProduit;
		}

		
		Etal trouverVendeur(Gaulois vendeur) {
			for (int i=0;i<nbrEtals;i++) {
				if (vendeur == (etals[i].getVendeur())) {
					return etals[i];
				}
			}
			return null;
		}
		
		String afficherMarche(){
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (int i=0;i<nbrEtals;i++) {
				if (!(etals[i].isEtalOccupe())) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalVide++;
				}
			}
			chaine.append("Il reste"+nbEtalVide+"etals non utilises dans le marche. \n");
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit , int nbProduit) {
		int indice = marche.trouverEtalLibre();
		marche.utiliserEtal(indice, vendeur, produit,nbProduit); 
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" a l'etal n°"+indice+".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		if (marche.trouverProduit(produit)[0] == null) {
			return "Il n'y a pas de vendeur qui propose des "+produit+" au marche.\n";
		}
		return "Seul le vendeur "+marche.trouverProduit(produit)[0].getVendeur().getNom()+" propose des "+produit+" au marche.\n";
	}
	
}