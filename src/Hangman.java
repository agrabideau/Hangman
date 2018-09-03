/**
 * Amanda Rabideau
 * CSIS-2450
 * A01 - Hangman
 * 09/08/18
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Hangman {

	private static int lives = 6;
	private static String secretWord = "thunder";  //Change this to test different words
	private static StringBuilder currentWord = new StringBuilder();
	private static boolean wordGuessed = false;
	private static List<Character> lettersGuessed = new ArrayList<>();

	public static void main(String[] args) {
		setUpCurrentWord();
		
		System.out.println("~~~~~~~~~~~~ Welcome to Hangman ~~~~~~~~~~~~");
		System.out.printf("Instructions:%nGuess the %d letter word, one letter at a time.  %nYou only have 6 chances.  %nGood luck!", secretWord.length());
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	
		Scanner in = new Scanner(System.in);
		while(lives > 0 && !wordGuessed) {	
			printGuessedLetters();	
			
			System.out.print("\nYour guess: ");
			String s = in.next();			
			while(!Character.isLetter(s.charAt(0)) || s.length() > 1) {
				System.out.print("Invalid guess. You can only guess letters, one at a time. Try again.     ");
				s = in.next();
			}
			
			printWord(s.charAt(0));
		}
		
		if(!wordGuessed) {
			System.out.println("Out of turns. You lose.");
		}	
	}
	
	// ----------------------- HELPER METHODS --------------------- //

	private static void printWord(Character ch) {
		// If the character is in the the word to be guessed, get the position of every place that character appears and save it.
		List<Integer> foundChars = new ArrayList<>();
		if (ch != null) {
			foundChars = findIndexOfChars(ch);
		}
		
		//Check that the character the user passed in has not already been guessed. If it has, make them guess another letter. Don't count this against their lives.
		if(ch != null && !lettersGuessed.contains(ch)) {
			verifyGuess(ch, foundChars);
		}		
	}
	
	/**
	 * Does most of the work.  Updates the currentWord, list of guessed letters, and number of lives.
	 * @param ch the letter being investigated
	 * @param foundChars the list of indices where the character is found (if applicable)
	 */
	private static void verifyGuess(Character ch, List<Integer> foundChars) {
		//If foundChars is not empty, get the currentWord and replace the "_" at that position with the real letter.
		if(foundChars.size() > 0) {
			for(Integer index : foundChars) {
				index *= 2; //Handles the extra space after each underscore
				currentWord.replace(index, index + 1, ch.toString());
			}
			lettersGuessed.add(ch);		
		} else { //If it is empty, it means the letter was not found in the secret word.  We don't replace anything in currentWord, but the player does lose a life.
			lives--;
			lettersGuessed.add(ch);
		}
		System.out.print(currentWord);
		System.out.printf(" (lives left: %d)%n", lives);
		
		//See if the user has the final word
		String currentWordNoSpaces = currentWord.toString().replaceAll(" ", "").toLowerCase();
		//System.out.println("Cuurrent word with no spaces: " + currentWordNoSpaces);
		//System.out.println("Secret word: " + secretWord);
		if(currentWordNoSpaces.equals(secretWord.toLowerCase())) {				
			System.out.println("You win!");
			wordGuessed = true;
		}
	}

	/**
	 * Finds all indices of a character if it is in the secret word.
	 * @param ch the character that is being guessed
	 * @return the index of each occurrence of that letter.
	 */
	private static List<Integer> findIndexOfChars(Character ch) {
		List<Integer> indexOfFoundChar = new ArrayList<>();

		//Lower case the character and secret word
		Character chLower = Character.toLowerCase(ch);
		String wordLower = secretWord.toLowerCase();
		//System.out.printf("Word in lowercase: %s ||| Character in lowercase: %s%n", wordLower, chLower);

		if (wordLower.contains(chLower.toString())) {
			for (int i = 0; i < wordLower.length(); i++) {
				if (wordLower.charAt(i) == chLower) {
					indexOfFoundChar.add(i);
				}
			}
		}

		return indexOfFoundChar;
	}
	
	/**
	 * Creates starting state of currentWord, based on length of secretWord
	 */
	private static void setUpCurrentWord() {
		for(int i = 0; i < secretWord.length(); i++) {
			currentWord.append("_ "); //Each character = 2 spaces. The spot for the character & the following whitespace so you can see how many letters are in the word.
		}
		//System.out.println("Current word: " + currentWord.toString());
	}
	
	/**
	 * Prints the letters that have already been guessed
	 */
	private static void printGuessedLetters() {
		if(lettersGuessed.size() != 0) {
			Collections.sort(lettersGuessed);
			System.out.print("\nLetters guessed: ");
			for(Character c : lettersGuessed) {
				System.out.print(c + "  ");
			}
			System.out.println();
		}
	}
}
