import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

/*
class JavaAdapter extends StatelessWidget {

  String text = 'Dolon kumar mondal';

  static const platform = MethodChannel('com.github.Arifuljava:GrozziieBlutoothSDk:v1.0.1');

  void showToast(String message) async {
    try {
      await platform.invokeMethod('printNow', {'message': message});
    } on PlatformException catch (e) {
      print('Failed to call Java function: ${e.message}');
    }
  }


  Future<void> passTextToJava(String text) async {
    try {
      await platform.invokeMethod('passTextToJava', {'text': text});
    } catch (e) {
      print('Failed to pass text to Java: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('Dolon kumar Mondal',style: TextStyle(fontSize: 22,fontWeight: FontWeight.bold),),
              SizedBox(height: 25),
              ElevatedButton(
                onPressed: () {
                  showToast('Now call the sdk from java');
                },
                child: const Text('Print Now'),
              ),

              SizedBox(height: 10,),
              ElevatedButton(
                child: Text('Pass Text to Java'),
                onPressed: () {
                  passTextToJava(text);
                },
              ),
            ],
          ),
        ),
    );
  }
}*/
