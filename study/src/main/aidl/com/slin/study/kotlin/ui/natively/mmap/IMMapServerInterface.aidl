// IMMapServerInterface.aidl
package com.slin.study.kotlin.ui.natively.mmap;

// Declare any non-default types here with import statements

interface IMMapServerInterface {

      void passShm(in ParcelFileDescriptor fd, int size);

      void writeText(String text);

      String readText();

}