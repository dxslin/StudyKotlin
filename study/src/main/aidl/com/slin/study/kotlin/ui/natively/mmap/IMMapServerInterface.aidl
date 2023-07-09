// IMMapServerInterface.aidl
package com.slin.study.kotlin.ui.natively.mmap;

// Declare any non-default types here with import statements

interface IMMapServerInterface {

      void passShm(int fd, in long[] addr, int size);

      void writeText(String text);

      String readText();

}