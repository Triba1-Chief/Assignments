#Samuel Kinyua
# Student ID removed
#10th March 2023
#This program will find words in a list of letters. Once all the words are
#found in the list, the program will search for free letters in the list
#sequentially. These letters will constitute the mystery word.

#CONSTANTS
LEFT = -1 #right to left
RIGHT = 1 #left to right
POSSIBLE_DIRECTIONS = LEFT, RIGHT 
FIRST_LETTER = 0 #index of the first letter in a word

def is_outside_list(letter_list, integer):
    if integer < 0 and integer > (len(letter_list) - 1):
        return True
    else:
        return False

def letter_positions(letter_list, character):
    position_list = []
    for index in range(len(letter_list)):
        if letter_list[index] == character:
            position_list.append(index)
    return position_list

def valid_word_pos_direction(letter_list, word, index, direction):
    for i in range(len(word)):
        if letter_list[index] == word[i] and not is_outside_list(letter_list, index):
            index = index + direction 
        else:
            return False
    return True

def direction_word_given_position(letter_list, word, index):
    list_directions = []
    if word[FIRST_LETTER] == letter_list[index]:
        for direction in POSSIBLE_DIRECTIONS:
            if valid_word_pos_direction(letter_list, word, index, direction):
                list_directions.append(direction)
    return list_directions

def position_direction_word(letter_list, word):
    dict_position_direction = {}
    for position in letter_positions(letter_list, word[FIRST_LETTER]):
        if not direction_word_given_position(letter_list, word, position) == []:
            dict_position_direction[position] = (direction_word_given_position
                                                 (letter_list, word, position))
    return dict_position_direction

def cross_word_position_direction(letter_list, word, index, direction):
    for i in range(len(word)):
        if direction == RIGHT:
            letter_list[index + i] = '*'     
        else:
            letter_list[index - i] = '*'

def cross_word_all_position_direction(letter_list,word,dict_position_direction):
    for position in dict_position_direction:
        for direction in dict_position_direction[position]:
            (cross_word_position_direction
             (letter_list, word, position, direction))

def find_magic_word(letter_list):
    hidden_word = ""
    for char in letter_list:
        if char != '*':
            hidden_word += char
    return hidden_word

def word_search(letter_list, word_list):
    magic_word = ""
    for word in word_list:
        dict_position_direction = position_direction_word(letter_list, word)
        (cross_word_all_position_direction
         (letter_list,word,dict_position_direction))
        magic_word = find_magic_word(letter_list)
    return magic_word

def word_search_main(filepath):
    read_data = []
    letter_list = []
    word_list = []
    new_word = ""
    fobj = open(filepath,'r')
    for line in fobj:
        read_data += [line]
    for letter in read_data[0]:
        if not letter == " " and not letter == '\n':
            letter_list += letter.upper()
    for word in read_data[1:]:   
        if word != '\n':
            for char in word:
                if not char == " " and not char == '\n':
                    new_word += char.upper()
            if new_word != "":
                word_list += [new_word]
                new_word = ""
    fobj.close()
    magic_word = word_search(letter_list, word_list)
    return magic_word
