package com.activemesa.solid.isp;

import sun.tracing.MultiplexProviderFactory;

public class Document {
}

interface Machine {
    void print(Document document);
    void fax(Document document);
    void scan(Document document);
}

class MultifunctionPrinter implements Machine {

    @Override
    public void print(Document document) {

    }

    @Override
    public void fax(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

class OldFashionPrinter implements Machine {

    @Override
    public void print(Document document) {

    }

    @Override
    public void fax(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

interface Printer {
    void print(Document document);
}

interface Scanner {
    void scan(Document document);
}

class JustPrinter implements Printer {

    @Override
    public void print(Document document) {

    }
}

class Photocopier implements Printer, Scanner {

    @Override
    public void print(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

interface MultiFunctionDevice extends Printer, Scanner {

}

class MultifunctionDeMachine implements MultiFunctionDevice {

    private Printer printer;
    private Scanner scanner;

    public MultifunctionDeMachine(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document document) {
        printer.print(document);
    }

    @Override
    public void scan(Document document) {
        scanner.scan(document);
    }
}