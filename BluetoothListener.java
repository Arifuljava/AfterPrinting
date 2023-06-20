package com.example.printer_ex;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

/**
 * MIT License
 *
 * Copyright (c) 2021 Prasad Parshram
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public interface BluetoothListener {

    /**
     * You can check all the bluetooth connection state with this listener.
     */
    interface onConnectionListener {
        void onConnectionStateChanged(BluetoothSocket socket, int state);
        void onConnectionFailed(int errorCode);
    }

    /**
     * You can read data with this listener.
     */
    interface onReceiveListener {
        void onReceived(String receivedData);
    }


    /**
     * You can detect nearby devices with this listener.
     */
    interface onDetectNearbyDeviceListener {
        void onDeviceDetected(BluetoothDevice device);
    }

    /**
     * You can get paired devices with this listener.
     */
    interface onDevicePairListener {
        void onDevicePaired(BluetoothDevice device);
        void onCancelled(BluetoothDevice device);
    }

    /**
     * You can get bluetooth discovery started or finished with this listener.
     */
    interface onDiscoveryStateChangedListener {
        void onDiscoveryStateChanged(int state);
    }
}