class No {
	public int elemento; 
	public No esq, dir; 
	public int nivel; //Calcula 

	public No(int elemento) {
		this(elemento, null, null, 1);
	}

	public No(int elemento, No esq, No dir, int nivel) {
		this.elemento = elemento;
		this.esq = esq;
		this.dir = dir;
		this.nivel = nivel;
	}

	public void setNivel() {
		this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir)); //pega o valor/tamanho do nível de maior lado(esq ou dir) + o nível deste própio nó 
	}

	public static int getNivel(No no) {
		return (no == null) ? 0 : no.nivel; //Se null não retorna nada, se não, retorna seu nível
	}
}


class arvoreAVL {
	private No raiz;

	public arvoreAVL() {
		raiz = null;
	}

	public boolean pesquisar(int x) {
		return pesquisar(x, raiz);
	}

	private boolean pesquisar(int x, No i) {
		boolean resp;
		if (i == null) {
			resp = false;
		} else if (x == i.elemento) {
			resp = true;
		} else if (x < i.elemento) {
			resp = pesquisar(x, i.esq);
		} else {
			resp = pesquisar(x, i.dir);
		}
		return resp;
	}

	public void caminharCentral() {
		System.out.print("[ ");
		caminharCentral(raiz);
		System.out.println("]");
	}

	private void caminharCentral(No i) {
		if (i != null) {
			caminharCentral(i.esq); 
			System.out.print(i.elemento + " "); 
			caminharCentral(i.dir); 
		}
	}

	public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);
	}

	private No inserir(int x, No i) throws Exception {
		if (i == null) {
			i = new No(x);
		} else if (x < i.elemento) {
			i.esq = inserir(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = inserir(x, i.dir);
		} else {
			throw new Exception("Erro ao inserir!");
		}
		return balancear(i);//DIFERENÇA DA AVL PRA UMA ÁROVRE NORMAL
	}

	public void remover(int x) throws Exception {
		raiz = remover(x, raiz);
	}

	private No remover(int x, No i) throws Exception {
		if (i == null) {
			throw new Exception("Erro ao remover!");
		} else if (x < i.elemento) {
			i.esq = remover(x, i.esq);
		} else if (x > i.elemento) {
			i.dir = remover(x, i.dir);
		} else if (i.dir == null) {
			i = i.esq;
		} else if (i.esq == null) {
			i = i.dir;
		} else {
			i.esq = maiorEsq(i, i.esq);
		}
		return balancear(i); //DIFERENÇA DA AVL PRA UMA ÁROVRE NORMAL
	}

	private No maiorEsq(No i, No j) {
		if (j.dir == null) {
			i.elemento = j.elemento; 
			j = j.esq;
		} else {
			j.dir = maiorEsq(i, j.dir);
		}
		return j;
	}

	private No balancear(No no) throws Exception {
		if (no != null) {
			int fator = No.getNivel(no.dir) - No.getNivel(no.esq); //CALCULA SEU FATOR: NIVEL DIR - ESQ
			if (Math.abs(fator) <= 1) { //SE FOR 0, 1 OU -1 NÃO SE ALTERA A ESTRURA DA ÁRVORE, POIS SÓ HÁ PROBLEMA SE OUVER UM DESNÍVEL DE PELO MENOS 2 NÍVEIS
				no.setNivel(); //ATUALIZA O NIVEL DOS NÓS POR CONTA DA NOVA FOLHA INSERIDA/RETIRADA
			} else if (fator == 2) { //DESBALANCEADO 2 PRA DIREITA
				int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq); //filho é no.dir pois está desbalanceado pra direita
				if (fatorFilhoDir == -1) { //se TRUE, será necessário uma rotação dupla, pois estará num formato V
					no.dir = rotacionarDir(no.dir); 
				}
				no = rotacionarEsq(no);
			} else if (fator == -2) {//DESBALANCEADO 2 PRA ESQUERDA
				int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq); //filho é no.esq pois está desbalanceado pra esquerda
				if (fatorFilhoEsq == 1) { //se TRUE, será necessário uma rotação dupla, pois estará num formato V
					no.esq = rotacionarEsq(no.esq);
				}
				no = rotacionarDir(no);
			} else {
				throw new Exception(
						"Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
			}
		}
		return no;
	}

	private No rotacionarDir(No no) {
		No noEsq = no.esq;
		No noEsqDir = noEsq.dir;

		noEsq.dir = no;
		no.esq = noEsqDir;
		no.setNivel(); 
		noEsq.setNivel(); 

		return noEsq;
	}

	private No rotacionarEsq(No no) {
		No noDir = no.dir;
		No noDirEsq = noDir.esq;

		noDir.esq = no;
		no.dir = noDirEsq;

		no.setNivel(); 
		noDir.setNivel(); 
		return noDir;
	}
}