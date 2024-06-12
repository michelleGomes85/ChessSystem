# Chess Game

Este é um projeto de um jogo de xadrez desenvolvido em Java. O objetivo é permitir que dois jogadores joguem uma partida de xadrez através de um terminal, utilizando as regras tradicionais do jogo.

## Estrutura do Projeto

O projeto está organizado nas seguintes pastas e arquivos:

- `application/`
  - `Program.java`: Classe principal que executa o jogo.
  - `UI.java`: Classe responsável pela interface do usuário no terminal.

- `boardgame/`
  - `Board.java`: Classe que representa o tabuleiro do jogo.
  - `Piece.java`: Classe que representa uma peça genérica no tabuleiro.
  - `Position.java`: Classe que representa uma posição no tabuleiro.

- `chess/`
  - `ChessException.java`: Classe que representa exceções específicas do xadrez.
  - `ChessMatch.java`: Classe que gerencia uma partida de xadrez.
  - `ChessPiece.java`: Classe abstrata que representa uma peça de xadrez.
  - `ChessPosition.java`: Classe que traduz posições de xadrez (e.g., a1, e5) para posições de tabuleiro.
  - `Color.java`: Enumeração que representa as cores das peças (PRETO e BRANCO).

- `chess/pieces/`
  - `Bishop.java`: Classe que representa a peça Bispo.
  - `King.java`: Classe que representa a peça Rei.
  - `Knight.java`: Classe que representa a peça Cavalo.
  - `Pawn.java`: Classe que representa a peça Peão.
  - `Queen.java`: Classe que representa a peça Rainha.
  - `Rook.java`: Classe que representa a peça Torre.

- `util/`
  - `Messages.java`: Interface que contém as mensagens utilizadas no jogo.
  - `Pieces.java`: Enumeração com os simbolos que representam cada peça do jogo (B, K, N, P, Q, R).

## Funcionalidades

- **Movimentação das Peças**: O jogo permite a movimentação de todas as peças de acordo com as regras oficiais do xadrez.
- **Promoção de Peões**: Quando um peão atinge a última linha do tabuleiro, ele pode ser promovido a outra peça (Rainha, Cavalo, Bispo, Torre).
- **Roque**: O jogo permite o movimento especial de roque.
- **Captura En Passant**: Implementa a captura especial de peões en passant.
- **Cheque e Cheque-Mate**: Verifica situações de cheque e cheque-mate.
- **Peças Capturadas**: Mantém uma lista de peças capturadas durante a partida.

## Como Executar

1. Clone o repositório para sua máquina local:
   ```bash
   git clone https://github.com/seu-usuario/chess-game.git

2. Execute o jogo:
   ```bash
   java application/Program.java

## Como Jogar
1. O tabuleiro será exibido no terminal.

      ```
            8 R N B Q K B N R
            7 P P P P P P P P
            6 - - - - - - - -
            5 - - - - - - - -
            4 - - - - - - - -
            3 - - - - - - - -
            2 P P P P P P P P
            1 R N B Q K B N R
              a b c d e f g h

        Captured Pieces
        White: []
        Black: []
        
        Turn: 1
        Waiting player: WHITE
        
        Source:

      ```
   
3. Você será solicitado a inserir a posição de origem e a posição de destino das peças.

      ```
            8 R N B Q K B N R
            7 P P P P P P P P
            6 - - - - - - - -
            5 - - - - - - - -
            4 - - - - - - - -
            3 - - - - - - - -
            2 P P P P P P P P
            1 R N B Q K B N R
              a b c d e f g h

        Target:

      ```
   
5. Para promover um peão, insira a letra correspondente à peça desejada (B para Bispo, N para Cavalo, R para Torre, Q para Rainha).
6. O jogo terminará quando ocorrer um cheque-mate.

Para a melhor experiência, recomenda-se usar um terminal colorido, como o Git Bash. 
Nesse ambiente, os jogadores serão diferenciados por cores: um será marcado como "WHITE" e o outro como "BLACK" aqui representado pelo cor amarela. 
Além disso, ao selecionar uma peça, o terminal destacará as possíveis movimentações dessa peça, facilitando a visualização e o planejamento das jogadas.
