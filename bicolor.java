class NoAN {
  public boolean cor; //se true: é gêmea de seu pai, se false, não é 
  public int elemento;
  public NoAN esq, dir;

  public NoAN() {
    this(-1);
  }

  public NoAN(int elemento) {
    this(elemento, false, null, null);
  }

  public NoAN(int elemento, boolean cor) {
    this(elemento, cor, null, null);
  }

  public NoAN(int elemento, boolean cor, NoAN esq, NoAN dir) {
    this.cor = cor;
    this.elemento = elemento;
    this.esq = esq;
    this.dir = dir;
  }
}


class bicolor{
   private NoAN raiz;

   public Alvinegra() {
      raiz = null;
   }

   public boolean pesquisar(int elemento) {
      return pesquisar(elemento, raiz);
   }

   private boolean pesquisar(int elemento, NoAN i) {
      boolean resp;
      if (i == null) {
         resp = false;
      } else if (elemento == i.elemento) {
         resp = true;
      } else if (elemento < i.elemento) {
         resp = pesquisar(elemento, i.esq);
      } else {
         resp = pesquisar(elemento, i.dir);
      }
      return resp;
   }

   public void caminharCentral() {
      System.out.print("[ ");
      caminharCentral(raiz);
      System.out.println("]");
   }
   private void caminharCentral(NoAN i) {
      if (i != null) {
         caminharCentral(i.esq); 
         System.out.print(i.elemento + ((i.cor) ? "(p) " : "(b) ")); 
         caminharCentral(i.dir); 
      }
   }

   public void inserir(int elemento) throws Exception { //inserção dos três primeiros elementos "manualmente", pois para inserir do jeito convencional precisamos do pai e do avô do nó inserido
      // Se a arvore estiver vazia
      if (raiz == null) {
         raiz = new NoAN(elemento);
         System.out.println("Antes, zero elementos. Agora, raiz(" + raiz.elemento + ").");

      // Senao, se a arvore tiver um elemento
      } else if (raiz.esq == null && raiz.dir == null) {
         if (elemento < raiz.elemento) {
            raiz.esq = new NoAN(elemento);
            System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e esq(" + raiz.esq.elemento + ").");
         } else {
            raiz.dir = new NoAN(elemento);
            System.out.println("Antes, um elemento. Agora, raiz(" + raiz.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }

      // Senao, se a arvore tiver dois elementos (raiz e dir)
      } else if (raiz.esq == null) {
         if (elemento < raiz.elemento) { //inserção do terceiro elemento balanceado(elemento < raiz.elemento < raiz.dir.elemento)
            raiz.esq = new NoAN(elemento);
            System.out.println("Antes, dois elementos(A). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else if (elemento < raiz.dir.elemento) {//inserção do terceiro elemento balanceado(raiz.elemento < elemento < raiz.dir.elemento)
            raiz.esq = new NoAN(raiz.elemento);
            raiz.elemento = elemento;
            System.out.println("Antes, dois elementos(B). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else {//inserção do terceiro elemento balanceado(raiz.elemento < raiz.dir.elemento < elemento)
            raiz.esq = new NoAN(raiz.elemento);
            raiz.elemento = raiz.dir.elemento;
            raiz.dir.elemento = elemento;
            System.out.println("Antes, dois elementos(C). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, se a arvore tiver dois elementos (raiz e esq)
      } else if (raiz.dir == null) {
         if (elemento > raiz.elemento) {//inserção do terceiro elemento balanceado(raiz.esq.elemento < raiz.elemento < elemento)
            raiz.dir = new NoAN(elemento);
            System.out.println("Antes, dois elementos(D). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else if (elemento > raiz.esq.elemento) {//inserção do terceiro elemento balanceado(raiz.esq.elemento < elemento < raiz.elemento)
            raiz.dir = new NoAN(raiz.elemento);
            raiz.elemento = elemento;
            System.out.println("Antes, dois elementos(E). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");

         } else { //inserção do terceiro elemento balanceado(elemento < raiz.esq.elemento < raiz.elemento)
            raiz.dir = new NoAN(raiz.elemento);
            raiz.elemento = raiz.esq.elemento;
            raiz.esq.elemento = elemento;
            System.out.println("Antes, dois elementos(F). Agora, raiz(" + raiz.elemento + "), esq (" + raiz.esq.elemento + ") e dir(" + raiz.dir.elemento + ").");
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, a arvore tem tres ou mais elementos (inserção padrão não manual)
      } else {
         System.out.println("Arvore com tres ou mais elementos...");
         inserir(elemento, null, null, null, raiz);
      }
      raiz.cor = false; 
   }

   private void inserir(int elemento, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
      if (i == null) {
         if (elemento < pai.elemento) {
            i = pai.esq = new NoAN(elemento, true);//todo elemento novo inserido é gemeo de seu pai(tirando os 3 primeiros)
         } else {
            i = pai.dir = new NoAN(elemento, true);//todo elemento novo inserido é gemeo de seu pai(tirando os 3 primeiros)
         }
         if (pai.cor == true) { //não se pode ter filho com cor true e pai com cor true(gêmeos consecutivos) 
            balancear(bisavo, avo, pai, i);
         }
      } else {
         // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if (i == raiz) {
               i.cor = false;
            } else if (pai.cor == true) {
               balancear(bisavo, avo, pai, i);
            }
         }
         if (elemento < i.elemento) {
            inserir(elemento, avo, pai, i, i.esq);
         } else if (elemento > i.elemento) {
            inserir(elemento, avo, pai, i, i.dir);
         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
   }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
      // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if (pai.cor == true) {
         // 4 tipos de reequilibrios e acoplamento
         if (pai.elemento > avo.elemento) { // rotacao a esquerda ou direita-esquerda
            if (i.elemento > pai.elemento) {
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }
         } else { // rotacao a direita ou esquerda-direita
            if (i.elemento < pai.elemento) {
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }
         if (bisavo == null) {
            raiz = avo;
         } else if (avo.elemento < bisavo.elemento) {
            bisavo.esq = avo;
         } else {
            bisavo.dir = avo;
         }
         // reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;
         System.out.println("Reestabeler cores: avo(" + avo.elemento + "->branco) e avo.esq / avo.dir("
               + avo.esq.elemento + "," + avo.dir.elemento + "-> pretos)");
      } // if(pai.cor == true)
   }

   private NoAN rotacaoDir(NoAN no) {
      System.out.println("Rotacao DIR(" + no.elemento + ")");
      NoAN noEsq = no.esq;
      NoAN noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private NoAN rotacaoEsq(NoAN no) {
      System.out.println("Rotacao ESQ(" + no.elemento + ")");
      NoAN noDir = no.dir;
      NoAN noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private NoAN rotacaoDirEsq(NoAN no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private NoAN rotacaoEsqDir(NoAN no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
   }
}