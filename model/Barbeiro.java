/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 05/06/2025
* Ultima alteracao.: 17/06/2025
* Nome.............: Barbeiro.java
* Funcao...........: Criar e configurar a Thread que representa o barbeiro no problema classico "Barbeiro Dorminhoco".
*************************************************************** */
package model;

import controller.ControllerTelaSimulacao;
import javafx.application.Platform;
import javafx.scene.control.Slider;

public class Barbeiro extends Thread {
  private ControllerTelaSimulacao controller;
  private Slider sliderVelocidade;
  /* ***************************************************************
  * Metodo: Barbeiro
  * Funcao: Construtor da classe Barbeiro. Ele inicializa a thread do barbeiro.
  * Parametros: ControllerTelaSimulacao controller: Controlador da tela de simulacao.
    Slider sliderVelocidade: Slider para controlar a velocidade do barbeiro.
  * Retorno: void
  *************************************************************** */
  public Barbeiro(ControllerTelaSimulacao controller, Slider sliderVelocidade) {
    this.controller = controller;
    this.sliderVelocidade = sliderVelocidade;
  }
  /* ***************************************************************
    * Metodo: getVelocidade
    * Funcao: Metodo para obter o valor do slider de velocidade.
    * Parametros: Nenhum.
    * Retorno: double - Retorna o valor do slider de velocidade.
    *************************************************************** */
  public double getVelocidade() {
    return sliderVelocidade.getValue(); // Retorna o valor do slider de velocidade
  }
  /* ***************************************************************
    * Metodo: sleepThread
    * Funcao: Metodo que faz a thread dormir por um tempo determinado.
      Ele simula o tempo de espera do barbeiro entre os cortes de cabelo.
    * Parametros: Nenhum.
    * Retorno: void
    *************************************************************** */
  public void sleepThread() {
    try {
      Thread.sleep(21);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println("Barbeiro interrompido.");
    }
  }
  /* ***************************************************************
    * Metodo: dormindo
    * Funcao: Metodo que simula o barbeiro dormindo quando clientes (puffles) nao estao sendo
      atendidos. Ele atualiza a interface para mostrar que o barbeiro esta dormindo.
    * Parametros: Nenhum.
    * Retorno: void
    *************************************************************** */
  public void dormindo() {
    
    Platform.runLater(() -> {
      controller.barbeiroDormindo.setVisible(true);
      controller.simboloDormindo.setVisible(true);
      controller.barbeiroCortando.setVisible(false);
      controller.tesouraCortando.setVisible(false);
    });
    sleepThread();
    // Verifica se o barbeiro esta pausado
    while (ControllerTelaSimulacao.barbeiroPausado) {
      try {
        Thread.sleep(100); // Espera enquanto esta pausado
      } catch (InterruptedException e) {
        System.out.println("Barbeiro interrompido.");
      }
    }
  }
  /* ***************************************************************
    * Metodo: cortaCabelo
    * Funcao: Metodo que simula o corte de cabelo do cliente (puffle).
    * Ele atualiza a interface para mostrar que o barbeiro esta cortando o cabelo.
    * Parametros: Nenhum.
    * Retorno: void
    *************************************************************** */
  public void cortaCabelo() {
    Platform.runLater(() -> {
      controller.barbeiroDormindo.setVisible(false);
      controller.simboloDormindo.setVisible(false);
      controller.barbeiroCortando.setVisible(true);
      controller.tesouraCortando.setVisible(true);
    });
    int tempoTotal = (int)(5000 / getVelocidade());
    int tempoPassado = 0;
    int fragmento = 20; // Milissegundos por fragmento

    while (tempoPassado < tempoTotal) {
      if (Thread.interrupted()) return;
      if (ControllerTelaSimulacao.barbeiroPausado) {
        try {
          Thread.sleep(100); // Espera enquanto esta pausado
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
        continue;
      }
      try {
        Thread.sleep(fragmento);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }
      tempoPassado += fragmento;
    }
  }
  /* ***************************************************************
  * Metodo: run
  * Funcao: Metodo que define o comportamento da thread Barbeiro.
    Ele simula o processo do barbeiro dormindo quando nao ha clientes (puffles)
    e acordando para cortar o cabelo de um cliente quando um puffle chega.
  * Parametros: Nenhum.
  * Retorno: void
  *************************************************************** */
  @Override
  public void run() {
    while (!this.isInterrupted()) { // Enquanto a thread nao for interrompida
      try {
        // Solucao do problema classico "Barbeiro Dorminhoco"
        if (ControllerTelaSimulacao.esperando == 0) {
          dormindo(); // O barbeiro dorme enquanto nao ha clientes (puffles) para atender
        }
        ControllerTelaSimulacao.clientes.acquire(); // O barbeiro dorme enquanto espera por um cliente (puffle)
        ControllerTelaSimulacao.mutex.acquire(); // O barbeiro entra na regiao critica
        ControllerTelaSimulacao.esperando--; // Decrementa o contador de clientes esperando
        ControllerTelaSimulacao.barbeiro.release(); // Libera o semaforo do barbeiro para indicar que ele esta pronto para cortar o cabelo
        ControllerTelaSimulacao.mutex.release(); // Libera a regiao critica
        cortaCabelo(); // O barbeiro corta o cabelo do cliente (puffle)
      } catch (InterruptedException e) {
        System.out.println("Barbeiro interrompido.");
        break; // Sai do loop se a thread for interrompida
      }
    }
  }
}