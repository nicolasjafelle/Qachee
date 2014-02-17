package com.android.qachee.sample;

import com.android.qachee.sample.domain.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolas on 2/17/14.
 */
public class DemoLoader {

	// Character(String name, String description, int imageResId)
	public static List<Character> createData() {
		com.android.qachee.sample.domain.Character homer = new Character("Homer Simpson", "Homer Jay Simpson is a cartoon character who appears in " +
			"the animated television series The Simpsons as the patriarch of the eponymous family.", R.raw.homer);

		com.android.qachee.sample.domain.Character bart = new Character("Bart Simpson", "Bartholomew JoJo \"Bart\" Simpson is a fictional main " +
			"character in the animated television series The Simpsons and part of the Simpson family. ", R.raw.bart);

		com.android.qachee.sample.domain.Character marge = new Character("Marge Simpson", "Marjorie \"Marge\" Simpson (née Bouvier) is a cartoon " +
			"character in the American animated sitcom The Simpsons and part of the eponymous family. ", R.raw.marge);

		com.android.qachee.sample.domain.Character lisa = new Character("Lisa Simpson", "Lisa Marie Simpson is one of the main characters in the " +
			"animated television series The Simpsons. ", R.raw.lisa);

		com.android.qachee.sample.domain.Character maggie = new Character("Maggie Simpson", "Margaret \"Maggie\" Simpson is a cartoon character in " +
			"the animated television series The Simpsons. ", R.raw.maggie);

		com.android.qachee.sample.domain.Character apu = new Character("Apu Nahasapeemapetilon", "Apu Nahasapeemapetilon is a fictional character " +
			"in the animated television series The Simpsons.", R.raw.apu);

		com.android.qachee.sample.domain.Character milhouse = new Character("Milhouse Van Houten", "Milhouse Mussolini Van Houten is a fictional " +
			"character featured in the animated television series The Simpsons, voiced by Pamela Hayden. ", R.raw.milhouse);

		com.android.qachee.sample.domain.Character moe = new Character("Moe Szyslak", "Moe Szyslak is a fictional character in the American " +
			"animated television series, The Simpsons. ", R.raw.moe);

		com.android.qachee.sample.domain.Character barney = new Character("Barney Gumble", "Barnard \"Barney\" Gumble is a character on the " +
			"American animated sitcom The Simpsons. ", R.raw.barney);

		com.android.qachee.sample.domain.Character comic = new Character("Comic Book Guy", "Comic Book Guy (CBG) is the common, " +
			"popular name for Jeff Albertson, a recurring fictional character in the animated television series The Simpsons. ", R.raw.comic);

		com.android.qachee.sample.domain.Character hibbert = new Character("Dr. Hibbert", "Dr. Julius M. Hibbert, M.D., " +
			"is a recurring character on the animated series The Simpsons. ", R.raw.doctor);

		com.android.qachee.sample.domain.Character mayor = new Character("Mayor Quimby", "Mayor Joseph \"Joe\" Quimby, nicknamed \"Diamond Joe," +
			"\" is a recurring character from the animated television series The Simpsons. ", R.raw.mayor);

		com.android.qachee.sample.domain.Character chief = new Character("Chief Wiggum", "Chief Clancy Wiggum is a fictional character from the " +
			"animated television series The Simpsons, voiced by Hank Azaria. ", R.raw.chief);

		List<Character> theSimpsons = new ArrayList<Character>();
		theSimpsons.add(homer);
		theSimpsons.add(bart);
		theSimpsons.add(marge);
		theSimpsons.add(lisa);
		theSimpsons.add(maggie);
		theSimpsons.add(apu);
		theSimpsons.add(milhouse);
		theSimpsons.add(moe);
		theSimpsons.add(barney);
		theSimpsons.add(comic);
		theSimpsons.add(hibbert);
		theSimpsons.add(mayor);
		theSimpsons.add(chief);

		return theSimpsons;
	}

}
