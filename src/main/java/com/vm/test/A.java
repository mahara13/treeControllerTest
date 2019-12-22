package com.vm.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class A {

    static A aSt = new A();

    B b = new B();

    A a = new A();

    A () {
        a = new A();
        b = new B();
    }

    public static void main (String [] arrg) {
        A a = new A();
    }

    public List<File> searchFile(String rootPath, String fileName) {
        List<File> result = new ArrayList<>();
        File root = new File(rootPath);
        if (root != null && fileName != null) {
            File[] files = root.listFiles();
            for (File iteratedFile: files) {
                if (iteratedFile.isDirectory()) {
                    result.addAll(searchFile(iteratedFile.getAbsolutePath(), fileName));
                } else if (iteratedFile.getName().equals(fileName)) {
                    result.add(iteratedFile);
                }
            }
        }

        return result;
    }

    public boolean isFileInDir(File rootFile, File testedFile) {
        return (rootFile != null & testedFile != null) ?
                testedFile.getAbsolutePath().startsWith(rootFile.getAbsolutePath()) :
                false;
    }

    class B{}
}


