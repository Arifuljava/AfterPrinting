/*import 'dart:typed_data';
import 'dart:ui' as ui;

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:barcode_widget/barcode_widget.dart';
import 'package:flutter/services.dart';
import 'package:firebase_storage/firebase_storage.dart' as firebase_storage;

class PrintSection extends StatefulWidget {
  const PrintSection({Key? key}) : super(key: key);

  @override
  State<PrintSection> createState() => _PrintSectionState();
}

class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Center(
              child: RepaintBoundary(
                key: globalKey,
                child: Container(
                  margin:
                  const EdgeInsets.symmetric(horizontal: 110, vertical: 30),
                  child: BarcodeWidget(
                    barcode: Barcode.code128(),
                    data: data,
                    drawText: true,
                    color: Colors.grey.shade700,
                    width: 150,
                    height: 70,
                  ),
                ),
              ),
            ),
            const SizedBox(height: 24),
            SizedBox(
              width: 300.0,
              child: TextField(
                onChanged: (value) {
                  setState(() {
                    data = value;
                  });
                },
                textAlign: TextAlign.center,
                style: const TextStyle(
                  color: Colors.black45,
                ),
                decoration: const InputDecoration(
                  hintText: "Type the Data",
                  filled: true,
                  fillColor: Colors.white,
                  border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6))),
                ),
              ),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                printContent();
              },
              child: const Text(
                "Convert to bitmap",
              ),
            ),
            if (imageData != null)
              Container(
                height: 200,
                width: 200,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: MemoryImage(imageData!),
                    fit: BoxFit.contain,
                  ),
                ),
              ),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            if (imageData != null) {
              sendBitmapToJava(imageData!);
            }
          },
          child: Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage();

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
        await sendBitmapToJava(imageData);
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage() async {
    try {
      RenderRepaintBoundary boundary =
      globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
      ui.Image image = await boundary.toImage(pixelRatio: 3.0);
      return image;
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendBitmapToJava(Uint8List bitmapData) async {
    try {
      await platform.invokeMethod('receiveBitmap', {'bitmapData': bitmapData});
      print('Bitmap data sent to Java');
    } catch (e) {
      print('Error sending bitmap data to Java: $e');
    }
  }
}*/

/*
class PrintSection extends StatefulWidget {
  const PrintSection({Key? key}) : super(key: key);

  @override
  State<PrintSection> createState() => _PrintSectionState();
}

class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Center(
              child: RepaintBoundary(
                key: globalKey,
                child: Container(
                  margin:
                  const EdgeInsets.symmetric(horizontal: 110, vertical: 30),
                  child: BarcodeWidget(
                    barcode: Barcode.code128(),
                    data: data,
                    drawText: true,
                    color: Colors.grey.shade700,
                    width: 150,
                    height: 70,
                  ),
                ),
              ),
            ),
            const SizedBox(height: 24),
            SizedBox(
              width: 300.0,
              child: TextField(
                onChanged: (value) {
                  setState(() {
                    data = value;
                  });
                },
                textAlign: TextAlign.center,
                style: const TextStyle(
                  color: Colors.black45,
                ),
                decoration: const InputDecoration(
                  hintText: "Type the Data",
                  filled: true,
                  fillColor: Colors.white,
                  border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6))),
                ),
              ),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                printContent();
              },
              child: const Text(
                "Convert to bitmap",
              ),
            ),
            if (imageData != null)
              Container(
                height: 200,
                width: 200,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: MemoryImage(imageData!),
                    fit: BoxFit.contain,
                  ),
                ),
              ),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            if (imageData != null) {
              uploadImageToFirebase(imageData!);
            }
          },
          child: Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage();

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage() async {
    try {
      RenderRepaintBoundary boundary =
      globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
      ui.Image image = await boundary.toImage(pixelRatio: 3.0);
      return image;
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> uploadImageToFirebase(Uint8List imageData) async {
    try {
      final storageRef = firebase_storage.FirebaseStorage.instance
          .ref()
          .child('images/image.png'); // Set the desired file path and name

      final uploadTask = storageRef.putData(imageData);

      final taskSnapshot = await uploadTask;

      final downloadURL = await taskSnapshot.ref.getDownloadURL();

      print('Image uploaded to Firebase Storage. Download URL: $downloadURL');
    } catch (e) {
      print('Error uploading image to Firebase Storage: $e');
    }
  }
}
*/

/*class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(height: 50),
              Center(
                child: RepaintBoundary(
                  key: globalKey,
                  child: Container(
                    margin: const EdgeInsets.symmetric(
                      horizontal: 110,
                      vertical: 30,
                    ),
                    child: BarcodeWidget(
                      barcode: Barcode.code128(),
                      data: data,
                      drawText: true,
                      color: Colors.grey.shade700,
                      width: 200,
                      height: 70,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 300.0,
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      data = value;
                      printContent(); // Call printContent when data is changed
                    });
                  },
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black45,
                  ),
                  decoration: const InputDecoration(
                    hintText: "Type the Data",
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6)),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (imageData != null) {
                    sendBitmapToJava(imageData!, 384, 200);
                  }
                },
                child: const Text(
                  "Convert to bitmap",
                ),
              ),
              if (imageData != null)
                Container(
                  height: 250,
                  width: 400,
                  decoration: BoxDecoration(
                    image: DecorationImage(
                      image: MemoryImage(imageData!),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            printContent();
          },
          child: const Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage(384, 200);

    if (image != null) {
      try {
        final imageData = await convertImageToData(image);
        setState(() {
          this.imageData = imageData;
        });
        await sendBitmapToJava(imageData!, 384, 200);
      } catch (e) {
        print('Error converting image to data: $e');
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage(
      double desiredWidth, double desiredHeight) async {
    try {
      RenderRepaintBoundary? boundary =
      globalKey.currentContext?.findRenderObject() as RenderRepaintBoundary?;
      if (boundary != null && boundary.hasSize) {
        ui.Image image = await boundary.toImage(pixelRatio: 3.0);
        ui.Image resizedImage =
        await resizeImage(image, desiredWidth, desiredHeight);
        return resizedImage;
      } else {
        print(
            'Error capturing container convert to bitmap: RenderRepaintBoundary is null or has no size');
        return null;
      }
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<ui.Image> resizeImage(
      ui.Image image, double desiredWidth, double desiredHeight) async {
    final img.Image resizedImage = img.copyResize(
      img.decodeImage(
          (await image.toByteData(format: ui.ImageByteFormat.rawRgba))
              ?.buffer
              .asUint8List() ??
              Uint8List(0))!,
      width: desiredWidth.toInt(),
      height: desiredHeight.toInt(),
    );
    final Uint8List resizedBytes = img.encodePng(resizedImage);
    final Completer<ui.Image> completer = Completer();
    ui.decodeImageFromList(resizedBytes, (result) {
      completer.complete(result);
    });
    return completer.future;
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendBitmapToJava(
      Uint8List bitmapData, double desiredWidth, double desiredHeight) async {
    try {
      final byteBuffer = bitmapData.buffer;
      final byteList = byteBuffer.asUint8List();
      await platform.invokeMethod('receiveBitmap', {
        'bitmapData': byteList,
        'desiredWidth': desiredWidth,
        'desiredHeight': desiredHeight,
      });
      print('Bitmap data sent to Java');
    } catch (e) {
      print('Error sending bitmap data to Java: $e');
    }
  }
}

class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(height: 50),
              Center(
                child: RepaintBoundary(
                  key: globalKey,
                  child: Builder(
                    builder: (BuildContext context) {
                      return Container(
                        margin:
                        const EdgeInsets.symmetric(horizontal: 110, vertical: 30),
                        child: BarcodeWidget(
                          barcode: Barcode.code128(),
                          data: data,
                          drawText: true,
                          color: Colors.grey.shade700,
                          width: 384,
                          height: 70,
                        ),
                      );
                    },
                  ),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 300.0,
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      data = value;
                    });
                  },
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black45,
                  ),
                  decoration: const InputDecoration(
                    hintText: "Type the Data",
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6)),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  printContent();
                },
                child: const Text(
                  "Convert to bitmap",
                ),
              ),
              if (imageData != null)
                Container(
                  height: 100,
                  width: 384,
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    image: DecorationImage(
                      image: MemoryImage(imageData!),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            printContent();
          },
          child: const Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage(384, 200);

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
        //await sendBitmapToJava(imageData);
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage(double desiredWidth, double desiredHeight) async {
    try {
      RenderRepaintBoundary? boundary = globalKey.currentContext != null
          ? (globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary)
          : null;
      if (boundary != null && boundary.hasSize) {
        ui.Image image = await boundary.toImage(pixelRatio: 3.0);
        ui.Image resizedImage = await resizeImage(image, desiredWidth, desiredHeight);
        return resizedImage;
      } else {
        print('Error capturing container convert to bitmap: RenderRepaintBoundary is null or has no size');
        return null;
      }
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<ui.Image> resizeImage(ui.Image image, double desiredWidth, double desiredHeight) async {
    final img.Image resizedImage = img.copyResize(
      img.decodeImage((await image.toByteData(format: ui.ImageByteFormat.rawRgba))?.buffer.asUint8List() ?? Uint8List(0))!,
      width: desiredWidth.toInt(),
      height: desiredHeight.toInt(),
    );
    final Uint8List resizedBytes = img.encodePng(resizedImage);
    final Completer<ui.Image> completer = Completer();
    ui.decodeImageFromList(resizedBytes, (result) {
      completer.complete(result);
    });
    return completer.future;
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendBitmapToJava(Uint8List bitmapData) async {
    try {
      final byteBuffer = bitmapData.buffer;
      final byteList = byteBuffer.asUint8List();
      await platform.invokeMethod('receiveBitmap', {'bitmapData': byteList});
      print('Bitmap data sent to Java');
    } catch (e) {
      print('Error sending bitmap data to Java: $e');
    }
  }
}*/

import 'dart:async';
import 'dart:typed_data';
import 'dart:ui' as ui;
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:image/image.dart' as img;
import 'package:flutter/services.dart';
import 'package:barcode_widget/barcode_widget.dart';

class PrintSection extends StatefulWidget {
  @override
  _PrintSectionState createState() => _PrintSectionState();
}

class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
      MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(height: 50),
              Center(
                child: RepaintBoundary(
                  key: globalKey,
                  child: Container(
                    //margin:
                        //const EdgeInsets.symmetric(horizontal: 110, vertical: 30),
                    color: Colors.white,
                    width: 384,
                    height: 384,
                    child: BarcodeWidget(
                      barcode: Barcode.code128(),
                      data: data,
                      drawText: true,
                      color: Colors.black,
                      width: 384,
                      height: 384,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 300.0,
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      data = value;
                    });
                  },
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black45,
                  ),
                  decoration: const InputDecoration(
                    hintText: "Type the Data",
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Colors.white),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  printContent();
                },
                child: const Text(
                  "Convert to bitmap",
                ),
              ),
              if (imageData != null)
                Container(
                  height: 100,
                  width: 384,
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    image: DecorationImage(
                      image: MemoryImage(imageData!),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            printContent();
          },
          child: const Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage();

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
       await sendBitmapToJava(imageData);
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage() async {
    try {
      RenderRepaintBoundary boundary =
          globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
      ui.Image image = await boundary.toImage(pixelRatio: 1.0);
      return image;
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
          await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendBitmapToJava(Uint8List bitmapData) async {
    try {
      final byteBuffer = bitmapData.buffer;
      final byteList = byteBuffer.asUint8List();
      await platform.invokeMethod('receiveBitmap', {'bitmapData': byteList});
      print('Bitmap data sent to Java');
    } catch (e) {
      print('Error sending bitmap data to Java: $e');
    }
  }
}

/*class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(height: 50),
              Center(
                child: RepaintBoundary(
                  key: globalKey,
                  child: Container(
                    margin:
                    const EdgeInsets.symmetric(horizontal: 110, vertical: 30),
                    child: BarcodeWidget(
                      barcode: Barcode.code128(),
                      data: data,
                      drawText: true,
                      color: Colors.white,
                      width: 384,
                      height: 70,
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 300.0,
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      data = value;
                    });
                  },
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black45,
                  ),
                  decoration: const InputDecoration(
                    hintText: "Type the Data",
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6)),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  printContent();
                },
                child: const Text(
                  "Convert to bitmap",
                ),
              ),
              if (imageData != null)
                Container(
                  height: 100,
                  width: 384,
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    image: DecorationImage(
                      image: MemoryImage(imageData!),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            printContent();
          },
          child: const Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage();

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
        await sendImageToJava(imageData);
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage() async {
    try {
      RenderRepaintBoundary? boundary =
      globalKey.currentContext?.findRenderObject() as RenderRepaintBoundary?;
      if (boundary != null) {
        ui.Image image = await boundary.toImage(pixelRatio: 3.0);
        return image;
      }
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
    }
    return null;
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendImageToJava(Uint8List imageData) async {
    try {
      final byteBuffer = imageData.buffer;
      final byteList = byteBuffer.asUint8List();
      await platform.invokeMethod('receiveImage', {'imageData': byteList});
      print('Image data sent to Java');
    } catch (e) {
      print('Error sending image data to Java: $e');
    }
  }
}*/



/*class _PrintSectionState extends State<PrintSection> {
  GlobalKey globalKey = GlobalKey();
  Uint8List? imageData;
  String data = "";

  static const platform =
  MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              SizedBox(height: 50),
              Center(
                child: RepaintBoundary(
                  key: globalKey,
                  child: Image.asset('assets/images/profile.png'),
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 300.0,
                child: TextField(
                  onChanged: (value) {
                    setState(() {
                      data = value;
                    });
                  },
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                    color: Colors.black45,
                  ),
                  decoration: const InputDecoration(
                    hintText: "Type the Data",
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderSide: BorderSide(color: Color(0xffD6D6D6)),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  printContent();
                },
                child: const Text(
                  "Convert to bitmap",
                ),
              ),
              if (imageData != null)
                Container(
                  height: 100,
                  width: 384,
                  decoration: BoxDecoration(
                    color: Colors.blue,
                    image: DecorationImage(
                      image: MemoryImage(imageData!),
                      fit: BoxFit.contain,
                    ),
                  ),
                ),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            printContent();
          },
          child: const Icon(Icons.print),
        ),
      ),
    );
  }

  void printContent() async {
    final image = await convertWidgetToImage();

    if (image != null) {
      final imageData = await convertImageToData(image);
      if (imageData != null) {
        setState(() {
          this.imageData = imageData;
        });
        await sendBitmapToJava(imageData);
      }
    }
  }

  Future<ui.Image?> convertWidgetToImage() async {
    try {
      RenderRepaintBoundary boundary =
      globalKey.currentContext!.findRenderObject() as RenderRepaintBoundary;
      ui.Image image = await boundary.toImage(pixelRatio: 3.0);
      return image;
    } catch (e) {
      print('Error capturing container convert to bitmap: $e');
      return null;
    }
  }

  Future<Uint8List?> convertImageToData(ui.Image image) async {
    try {
      ByteData? byteData =
      await image.toByteData(format: ui.ImageByteFormat.png);
      return byteData?.buffer.asUint8List();
    } catch (e) {
      print('Error converting image to data: $e');
      return null;
    }
  }

  Future<void> sendBitmapToJava(Uint8List bitmapData) async {
    try {
      final byteBuffer = bitmapData.buffer;
      final byteList = byteBuffer.asUint8List();
      await platform.invokeMethod('receiveBitmap', {'bitmapData': byteList});
      print('Bitmap data sent to Java');
    } catch (e) {
      print('Error sending bitmap data to Java: $e');
    }
  }
}*/
