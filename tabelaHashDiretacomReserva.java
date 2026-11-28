
class Hash {
   int tabela[];//vetor que guarda os elementos
   int m1; //tamanho área principal
   int m2; //tamanho área reserva
   int m; //tamanho total(m1 + m2)
   int reserva;//quantos elementos há na reserva
   final int NULO = -1; //valor -1, que marca uma posição vazia

   public Hash() {
      this(13, 7); //valore padrão para área principal e reserva
   }

   public Hash(int m1, int m2) {
      this.m1 = m1;
      this.m2 = m2;
      this.m = m1 + m2;
      this.tabela = new int[this.m];
      for (int i = 0; i < m1; i++) {
         tabela[i] = NULO; //definindo todos os valores da tabela, inicialmente, como nulos
      }
      reserva = 0; //inicialmente, não há nenhum elemento na reserva 
   }

   public int h(int elemento) {//regra para definição de posição
      return elemento % m1; 
   }

   public boolean inserir(int elemento) {
      boolean resp = false;
      if (elemento != NULO) { //se o elemento a ser inserido não for nulo
         int pos = h(elemento);
         if (tabela[pos] == NULO) { //se a posição na tabela principal estiver sem nenhum elemento, nula
            tabela[pos] = elemento;
            resp = true;
         } else if (reserva < m2) {//se a posição na tabela principal estiver ocupada, mas há espaço na reserva
            tabela[m1 + reserva] = elemento;
            reserva++;
            resp = true;
         }
      }
      return resp;
   }

   public boolean pesquisar(int elemento) {
      boolean resp = false;
      int pos = h(elemento); //pega a posição
      if (tabela[pos] == elemento) { //se o elemento na devida posição for o que procuramos, RESP = TRUE
         resp = true;
      } else if (tabela[pos] != NULO) { //senão, se a posição na tabela não estiver nula, quer dizer que pode haver outros elemento que estariam naquela posição, só que na reserva 
         for (int i = 0; i < reserva; i++) {//procuramos na reserva
            if (tabela[m1 + i] == elemento) {
               resp = true;
               i = reserva;
            }
         }
      }
      return resp;
   }

   boolean remover(int elemento) {
      boolean resp = false;
      // ...
      return resp;
   }
}