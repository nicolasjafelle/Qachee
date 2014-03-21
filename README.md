![Qachee-logo](https://raw2.github.com/nicolasjafelle/Qachee/master/QacheeProject/QacheeSample/src/main/res/drawable-mdpi/ic_launcher.png)Qachee
======
Qachee is a generic cache system to use specially for Android Apps. It is just a LRU Memory cache with policy expiration and lots of useful methods.<br>

Instructions 1
============

1. Clone the git repo
2. Import the "Qachee" module into your Android-gradle project.
3. Add "Qachee" module in your settings.gradle
4. DONE

Instructions 2 
============

1. Add Nicolas Jafelle's Maven repo to your build.gradle: <a href="https://github.com/nicolasjafelle/maven-repo">Instructions</a>
2. add this dependency: 'com.qachee:qachee:1.0'
3. DONE


How to Use it
================

First you need to extends the QacheeableObject which implements the Qacheeable interface, then you have to implements getKey, you can return the ID attribute or anything that make your instance unique, like hashcode()<br>
``` java
@Override
public Long getKey() {
	return (long)hashCode();
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
Character character = QacheeManager.getInstance().get(getItem(position), Character.class);
```
OR...
``` java
// If toArray() returns empty means: there is nothing previously stored.
// If toArray() returns null the stored data is no longer valid.
List<Character> list = QacheeManager.getInstance().toArray(Character.class);

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
selectedCharacter = (Character) QacheeManager.getInstance().get(selectedCharacter);
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
