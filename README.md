![Qachee-logo](https://raw.githubusercontent.com/nicolasjafelle/Qachee/master/QacheeProject/QacheeSample/src/main/res/drawable-hdpi/ic_launcher.png)Qachee
======

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Qachee-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1276)

Qachee is a generic cache system to use specially for Android Apps. It is just a LRU Memory cache with policy expiration and lots of useful methods.<br>

Instructions - Maven Central
============

1. Add this library in your build.gradle:
```groovy
dependencies {
    compile 'com.github.nicolasjafelle:qachee:1.2'
}
```

Instructions - Android Library Module
============

1. Clone the git repo
2. Import the "Qachee" module into your Android-gradle project.
3. Add "Qachee" module in your settings.gradle
4. DONE

How to Use it
================

First you need to extends the QacheeableObject which implements the Qacheeable interface, then you have to implements getKey, you can return a String value or anything that make your instance unique, like getClass().getSimpleName() + hashcode()<br>
``` java
@Override
public Long getKey() {
	Log.i("HASHCHODE", "KEY VALUE FOR " + getName() + "= " + getClass().getSimpleName() + hashCode());
	return getClass().getSimpleName() + hashCode();
}
```
You can set the Expiration Time Policy, default is: 
``` java 
ExpirationTime.ONE_MINUTE 
```

Simple in your Application or any place use:
``` java
QacheeManager.getInstance().setExpirationTime(ExpirationTime.THIRTY_SECONDS);
```

Then just simple use QacheeManager to add, remove and get your Qacheeable objects.<br>
``` java
Character character = QacheeManager.getInstance().get(getItem(position), Character.class, true);
```
OR...
``` java
// If toArray() returns empty means: there is nothing previously stored.
// If toArray() returns null the stored data is no longer valid.
List<Character> list = QacheeManager.getInstance().toArray(Character.class, true);

if(list == null || list.isEmpty()) {
	new DemoTask(getActivity()).execute();
}else {
	adapter = new CharacterAdapter(list);
	listView.setAdapter(adapter);
}
```

When your object is stored in the Qachee you do not need to re-add or update it, simple use it. REMEMBER always to check if the object is stored in Qachee, REMEMBER always work with Qachee, like this:<br>
``` java
Character selectedCharacter = (Character) listView.getItemAtPosition(pos);
selectedCharacter = (Character) QacheeManager.getInstance().get(selectedCharacter, true);
```

See <a href="https://github.com/nicolasjafelle/Qachee/tree/master/QacheeProject/QacheeSample">Demo Sample</a>

Google Play demo: <a href="https://play.google.com/store/apps/details?id=com.qachee.sample">link</a>


Developed By
================

* Nicolas Jafelle - <nicolasjafelle@gmail.com>


License
================

    Copyright 2014 Nicolas Jafelle

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
