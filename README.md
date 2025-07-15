````markdown
# 📌 Problema do Barbeiro Dorminhoco

<p align="center">
  <img src="gifs/barbeiro_dormindo.gif" alt="Barbeiro Dormindo" width="300" />
  <img src="gifs/cliente_entrando.gif" alt="Cliente Entrando" width="300" />
</p>

**Autor:** Gabriel Marcone Magalhaes Santos  
**Matrícula:** 202410374  
**Início:** 05/06/2025  
**Última alteração:** 17/06/2025

---

## 📖 Sobre o Projeto
Este projeto ilustra a **solução concorrente** do clássico **Problema do Barbeiro Dorminhoco**, implementada em **JavaFX**. A simulação mostra um barbeiro (pinguim) que dorme enquanto não há clientes (puffles) e acorda para atendê-los quando chegarem, usando **threads**, **semáforos** e **FXML** para interface animada.

---

## 🎯 Objetivos do Projeto
✅ Entender a sincronização de **threads** usando **semáforos**.  
✅ Garantir **exclusão mútua** e **evitar deadlock** e **starvation**.  
✅ Controlar a chegada de clientes por meio de um **GeradorDeClientes**.  
✅ Simular estados de clientes (entrando, aguardando, sendo atendido, saindo satisfeito/bravo).  
✅ Integrar animações em **GIF** e controle de velocidade via **sliders**.

---

## 🛠️ Componentes Utilizados
- **Java 8+** com **JavaFX**  
- **Threads** (`model.Barbeiro`, `model.Cliente`, `model.GeradorDeClientes`)  
- **Semáforos** (`clientes`, `barbeiro`, `mutex`) em `ControllerTelaSimulacao`  
- **FXML** para layout (`view/scene1.fxml`, `scene2.fxml`, `info.fxml`)  
- **Controle de UI**: botões, sliders e imagens animadas (`controller.*`)

---

## 📸 Demonstração do Projeto

* **Barbeiro dormindo**: `gifs/barbeiro_dormindo.gif`
* **Cliente entrando e aguardando**: `gifs/cliente_entrando.gif`

> 🎥 Em breve, vídeo demonstrativo disponível na pasta `docs/`.

```
```
