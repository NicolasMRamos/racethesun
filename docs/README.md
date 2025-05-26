# LDTS_1301 - Race The Sun

---

## Descrição

Race The Sun é um jogo infinito que desafia suas habilidades de reação. No comando de uma pequena nave de corrida, o objetivo do jogador é ir o mais longe possível, desviando dos obstáculos pelo caminho.

---

## Desenvolvedores

Este projeto foi desenvolvido por *Maurício Sardinha* (up202305312), *Miguel Souto* (up202210671) e *Nicolas Ramos* (up202304442) para a disciplina de LDTS 24/25.

---

## Inspiração
Baseamos nosso projeto no jogo *Race The Sun*, da desenvolvedora Flippfly. O jogo está disponível para jogar nos dispositivos móveis e nos computadores, pela Steam.  
![](https://i.imgur.com/fp33NES.png)

---

## Funcionalidades

**Menus** - O jogo tem um menu principal e um menu de morte que aparece após colidir com um obstáculo.

**Movimentação** - A nave se move automaticamente para frente, e o jogador pode apenas movê-la para a esquerda e para a direita.

**Obstáculos** - Ao colidir com um obstáculo, o jogador perde o jogo.

**Procedural Generation** - Conforme o jogador avança, os obstáculos vão sendo gerados automaticamente e aleatoriamente.

**Grid do mapa** - Ajuda o jogador a perceber onde está, e por onde pode prosseguir.

### Screenshots Ilustrativas

**Menu Principal**

![Menu principal](https://imgur.com/PPgI5pe.png)

**Menu de morte**

![Menu de morte](https://imgur.com/gozOJ8l.png)

**Nave reta com obstáculos visíveis**

![Nave indo reto com obstáculos visíveis](https://imgur.com/h5GYgcW.png)

**Nave indo para esquerda**

![Nave indo para esquerda](https://imgur.com/9XRk1lP.png)

**Nave indo para direita**

![Nave indo para direita](https://imgur.com/s7PYKDM.png)

**Procedural Generation visível**

![Procedural Generation](https://imgur.com/SCgjTJe.png)

---

## *Architectural Pattern* Aplicado
**MVC:** *Model-View-Controller*

O MVC é claramente visível em todo o projeto, com cada parte do jogo tendo um modelo, vizualisador e controlador.

---

## *Design Patterns* Utilizados

### Game Loop

O Game Loop é o *backbone* do jogo, garantindo que ele funcione de forma contínua e fluida. É responsável por:

**Processar inputs**: Obtém as ações do jogador via teclado ou outros dispositivos.

**Atualizar a lógica**: Atualiza o estado do jogo (como movimentação da nave, colisões, e física) em intervalos fixos.

**Renderizar**: Chama os visualizadores (Viewer) para desenhar os elementos atualizados na tela.

**Controle de tempo**: Mantém o jogo rodando a uma taxa constante, mesmo em máquinas com diferentes capacidades de processamento.

A implementação do game loop é feita na classe Game, no método start().

### State

Há dois estados para o jogo: **menu** e **dentro do jogo**. Isso ajuda a separar as ações tomadas e seus resultados, levando a diferentes interpretações e comportamentos dependendo do estado atual.

### Singleton

O jogo é gerenciado como uma única instância global, com seus recursos sendo controlados durante todo o tempo de vida da aplicação.

### Composite

Os elementos do jogo, como câmera, nave, obstáculos e raios, derivam da classe base Element, formando uma hierarquia que permite tratá-los de maneira uniforme.

### Builder

Usado para construir o mapa do jogo com todos os seus elementos: nave, obstáculos, raios (para raycasting), chão, céu, e a grid. É uma construção complexa, realizada passo a passo.

### Adapter

A classe LanternaGUI encapsula métodos da API do Lanterna para serem utilizados na aplicação, adaptando a biblioteca ao contexto do jogo. Além disso, a classe KeyHandler funciona como um adaptador para inputs do teclado, traduzindo-os para ações compreendidas pelo jogo.

### Factory Method

Presente na classe ArenaBuilder para construir elementos individuais do mapa, como a nave, os obstáculos, e a grid, delegando a criação específica à subclasse: RandomArenaBuilder.

### Abstract Factory

**State**: Usado para criar e gerenciar os dois estados principais do jogo (GameState e MenuState), cada um com seus próprios comportamentos e recursos.

**Viewer**: Determina os diversos visualizadores (HeroViewer, RectViewer, etc.) para cada elemento do jogo. Cada derivado de Viewer trabalha em conjunto com os outros visualizadores relacionados.

---

## Design

### Game Loop

#### Problema em Contexto

O jogo precisava de um mecanismo para atualizar continuamente o estado do jogo enquanto mantinha uma experiência fluida para o jogador. Isso envolve processar inputs, atualizar a lógica do jogo e renderizar os visuais em uma taxa de atualização constante. Sem esse mecanismo, o jogo seria estático ou teria comportamentos inconsistentes.

#### Padrão Aplicado

Aplicamos o padrão do *Game Loop*, que garante que o jogo funcione em um ciclo contínuo, processando entradas, atualizando a lógica e renderizando a saída em cada iteração. Este padrão foi escolhido porque é uma solução padrão para jogos em tempo real, garantindo atualizações previsíveis e renderização suave.

#### Implementação

O *Game Loop* é implementado na classe *Game*. O método *start()* gerencia o fluxo:

**1** - Processa os inputs através da GUI.

**2** - Atualiza a lógica do jogo chamando o método step() no estado atual.

**3** - Renderiza os visuais invocando o correspondente Viewer.

#### Consequências

**Benefícios**:

Garante um comportamento consistente em diferentes máquinas utilizando um tempo fixo por atualização.

Desacopla o processamento de input, atualizações de lógica e renderização.

**Desvantagens**:

Aumenta a complexidade, exigindo cuidado com o controle de tempo e sincronização.

### State

#### Problema em Contexto

O jogo possui dois modos principais: **menu** e **jogabilidade**. Cada modo tem comportamentos diferentes e requer uma lógica distinta. Era já um problema que tínhamos em mente antes de iniciar o projeto.

#### Padrão Aplicado

Aplicamos o padrão *State* para separar a lógica do menu e da jogabilidade em classes distintas. Isso permite que cada estado defina seu comportamento de forma independente, reduzindo a complexidade e melhorando a manutenção do código.

#### Implementação

A classe *State* define uma estrutura abstrata para os estados, com *GameState* e *MenuState* implementando comportamentos específicos. A classe *Game* gerencia as transições entre esses estados.

#### Consequências

**Benefícios**:

Melhora a modularidade, encapsulando comportamentos específicos do estado.

**Desvantagens**:

Requer classes adicionais, aumentando o número de arquivos para gerenciar.

### Builder

#### Problema em Contexto

O mapa do jogo (Arena) é composto por múltiplos elementos (nave, obstáculos, raios, chão, céu, *grid*), que são interdependentes e complexos de criar. Construir esses elementos diretamente em uma única etapa poderia levar a erros.

#### Padrão Aplicado

Aplicamos o padrão *Builder* para construir o mapa etapa por etapa. Essa abordagem encapsula a lógica de criação e permite a construção do mapa de forma aleatória (*Procedural Generation*).

#### Implementação

A classe *ArenaBuilder* fornece uma template para construir o mapa. A subclasse *RandomArenaBuilder* implementa a lógica para gerar o mapa proceduralmente.

#### Consequências

**Benefícios**:

Simplifica a construção de objetos complexos.

Encapsula a lógica de criação do mapa, permitindo fácil personalização (neste caso, escolhemos uma personalização aleatória).

**Desvantagens**:

Requer classes adicionais para os *builders*.

### Adapter

#### Problema em Contexto

Utilizar diretamente a API do Lanterna para o jogo acoplava a lógica do mesmo à biblioteca, dificultando futuras alterações, substituições e utilizações.

#### Padrão Aplicado

Aplicamos o padrão *Adapter* para desacoplar a lógica do jogo da API do Lanterna. A classe *LanternaGUI* traduz chamadas específicas do jogo em chamadas da biblioteca. A classe *KeyHandler* adapta os inputs do teclado para ações compreendidas pelo jogo, que nós determinamos.

#### Implementação

A interface GUI abstrai as operações da interface gráfica, enquanto a classe *LanternaGUI* implementa essas operações utilizando a API do Lanterna.

#### Consequências

**Benefícios**:

Desacopla a lógica do jogo do Lanterna.

**Desvantagens**:

Exige a implementação da lógica de adaptação para cada funcionalidade utilizada da biblioteca.

### Abstract Factory

#### Problema em Contexto

O jogo precisa criar objetos relacionados de forma consistente (por exemplo, controladores e visualizadores para estados, visualizadores para elementos do jogo). Sem uma abordagem consistente, é difícil manter a coerência entre esses objetos relacionados.

#### Padrão Aplicado


Aplicamos o padrão *Abstract Factory* para criar famílias de objetos relacionados:

A classe *State* atua como uma fábrica abstrata para criar *Controllers* e *Viewers* para cada estado do jogo.

A classe *Viewer* determina o visualizador apropriado (*HeroViewer*, *RectViewer*, etc.) para cada elemento do jogo.

#### Implementação

Os métodos abstratos em *State* (*getViewer*, *getController*) e *Viewer* (*drawElements*) definem a interface da fábrica, enquanto as subclasses concretas fornecem implementações específicas.

#### Consequências

**Benefícios**:

Garante consistência entre objetos relacionados.

Simplifica a adição de novos estados ou visualizadores.

**Desvantagens**:

Adiciona complexidade devido à necessidade de múltiplas classes abstratas e concretas.


---

## *Smells* do Código

- Métodos vazios: *CameraController: getModel()* e *RandomArenaBuilder: createArena()*

Simplificam a leitura do código onde são chamados posteriormente.
- printStackTrace() utilizado ao invés de um *logging* mais robusto.

Devido às condições do projeto, não achamos necessário elaborar para algo mais complexo.
- Uso de API obsoleto do Lanterna: *TextCharacter* na classe *LanternaGUI* (e tester).

Utilizar essa classe era o modo mais fácil de chegar no que queríamos.

---

## Testes

- Tabela de Coverage:
  ![](https://imgur.com/cVpWwOE.png)
- Relatório do pitest:
  ![](https://imgur.com/JIEkpwA.png)
Nota: Escolhemos excluir a classe Application do relatório (já que não precisavamos testar essa classe) e as classes Game e GameTest (visto que os testes estavam causando conflitos com o pitest).

---

## Avaliação Individual

- Nicolas Ramos: 33%
- Miguel Souto: 33%
- Maurício Sardinha: 33%

O projeto foi bem distribuído entre os três integrantes do grupo.
