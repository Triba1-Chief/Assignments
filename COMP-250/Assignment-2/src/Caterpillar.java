package assignment2;

import java.awt.Color;
import java.util.Random;
import java.util.Stack;

import assignment2.food.*;


/* Assignment Assumptions:
1. Gus will always move before attempting to eat
2. Gus will eat only when it is in a feeding stage
3. Methods will be tested only on valid inputs: null is not a valid input for any method.
 */


public class Caterpillar {
	public Segment head;
	public Segment tail;
	public int length;
	public EvolutionStage stage;
	public Stack<Position> positionsPreviouslyOccupied;
	public int goal;
	public int turnsNeededToDigest;
	public static Random randNumGenerator = new Random(1);

	public Caterpillar(Position p, Color c, int goal) {
		this.head = new Segment(p,c);
		this.tail = this.head;
		this.length++;
		this.stage = EvolutionStage.FEEDING_STAGE;
		this.goal = goal;
		this.positionsPreviouslyOccupied = new Stack<Position>();
	}

	public EvolutionStage getEvolutionStage() {
		return this.stage;
	}

	public Position getHeadPosition() {
		return this.head.position;
	}

	public int getLength() {
		return this.length;
	}

	public Color getSegmentColor(Position p) {
		Segment current = head;

		for (int i = 0; i < this.getLength(); i++) {
			if (current.position.equals(p)) {
				return current.color;
			}
			else {
				current = current.next;
			}
		}

		return null;
	}

	public void move(Position p) {
		if(this.getEvolutionStage() == EvolutionStage.ENTANGLED) {
			return;
		}

		double range = Position.getDistance(new Position(this.getHeadPosition()), new Position(p));
		if (range != 1) {
			throw new IllegalArgumentException();
		}

		Segment newHead = new Segment(p, this.head.color);
		newHead.next = this.head;
		this.head = newHead;

		Segment current = this.head.next;
		Segment previous = this.head;
		int i = 0;
		while (i < this.getLength()) {
			if (current.next != null) {
				current.color = current.next.color;
				previous = current;
				current = current.next;
			} else {
				this.positionsPreviouslyOccupied.push(current.position);
				this.tail = previous;
				previous.next = null;
			}
			i++;
		}

		Segment currentPosition = this.head.next;

		while (currentPosition != null) {
			if (this.getHeadPosition().equals(this.tail.position) || currentPosition.position.equals(p)) {
				this.stage = EvolutionStage.ENTANGLED;
				return;
			}
			currentPosition = currentPosition.next;
		}

		if (this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			if (this.getEvolutionStage() == EvolutionStage.BUTTERFLY || this.getEvolutionStage() == EvolutionStage.ENTANGLED) {
				return;
			}

			if (this.turnsNeededToDigest == 0) {
				this.stage = EvolutionStage.FEEDING_STAGE;
			}
			else {
				if (this.turnsNeededToDigest > 0) {
					Segment newTail;
					currentPosition = this.head;
					Position compare = this.positionsPreviouslyOccupied.pop();

					while (currentPosition != null) {
						if (!(compare.equals(currentPosition.position))) {
							currentPosition = currentPosition.next;
						}
						else {
							this.positionsPreviouslyOccupied.push(compare);
							return;
						}
					}

					int k = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);

					newTail = new Segment(compare,GameColors.SEGMENT_COLORS[k]);
					this.tail.next = newTail;
					this.tail = newTail;
					this.length++;

					if (this.getLength() >= this.goal) {
						this.stage = EvolutionStage.BUTTERFLY;
						return;
					}

					this.turnsNeededToDigest--;
				}
			}
		}
	}

	public void eat(Fruit f) {
		if(this.getEvolutionStage() == EvolutionStage.BUTTERFLY || this.getEvolutionStage() == EvolutionStage.ENTANGLED) {
			return;
		}

		Color fruitColour = f.getColor();
		Segment newTail;
		Position prevPosition = this.positionsPreviouslyOccupied.pop();

		if (!(this.getHeadPosition().equals(prevPosition))) {
			newTail = new Segment(prevPosition, fruitColour);

			if (this.tail == this.head) {
				this.head.next = newTail;
			}
			else {
				this.tail.next = newTail;
			}

			this.tail = newTail;
			this.length++;

			if (this.getLength() >= this.goal) {
				this.stage = EvolutionStage.BUTTERFLY;
			}
		}
		else {
			this.stage = EvolutionStage.GROWING_STAGE;
			this.turnsNeededToDigest++;
			this.positionsPreviouslyOccupied.push(prevPosition);
		}
	}

	public void eat(Pickle p) {
		if(this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			return;
		}

		Segment current = this.head;
		int i = 0;

		while (i < this.getLength()) {
			if (current.next != null) {
				current.position = current.next.position;
				current = current.next;
			}
			else {
				this.tail.position = this.positionsPreviouslyOccupied.pop();
			}
			i++;
		}
	}

	public void eat(Lollipop lolly) {
		if(this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			return;
		}

		Color[] colorsArray = new Color[this.getLength()];
		Segment current = this.head;

		for (int i = 0; i < this.getLength(); i++) {
			colorsArray[i] = getSegmentColor(current.position);
			current = current.next;
		}

		for (int i = colorsArray.length - 1; i > 0; i-- ) {
			int j = randNumGenerator.nextInt(i + 1);

			Color temp = colorsArray[i];
			colorsArray[i] = colorsArray[j];
			colorsArray[j] = temp;
		}

		current = this.head;
		for (int i = 0; i < this.getLength(); i++) {
			current.color = colorsArray[i];
			current = current.next;
		}
	}

	public void eat(IceCream gelato) {
		if(this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			return;
		}

		Segment current = this.head;
		Segment[] segmentsArray = new Segment[this.getLength()];

		for (int i = 0; i < this.getLength(); i++) {
			segmentsArray[i] = current;
			current = current.next;
		}

		for (int i = segmentsArray.length - 1; i > 0; i--) {
			segmentsArray[i].next = segmentsArray[i - 1];
		}

		this.head = segmentsArray[segmentsArray.length - 1];
		this.head.color = GameColors.BLUE;
		this.tail = segmentsArray[0];
		this.tail.next = null;
		this.positionsPreviouslyOccupied.clear();
	}

	public void eat(SwissCheese cheese) {
		if(this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			return;
		}

		Segment [] tempSegments = new Segment[this.getLength()];
		Segment current = this.head;
		int counter = 0;
		int halfway = (this.getLength() + 1)/2;

		for (int i = 0; i < this.getLength(); i++) {
			if ((i+1) <= halfway) {
				if ((i + 1) == halfway) {
					this.tail = current;
				}

				if (current.equals(this.head)) {
					current = current.next;
					continue;
				}

				if (current.equals((this.head.next))) {
						current.color = getSegmentColor(current.next.position);
				}
				else {
					if (current.next.next != null) {
						current.color = getSegmentColor(current.next.next.position);
					}
				}
			}
			else {
				tempSegments[counter] = current;
				counter++;
			}

			current = current.next;
		}

		this.tail.next = null;
		this.length = halfway;

		while (counter != 0) {
			this.positionsPreviouslyOccupied.push(tempSegments[(counter - 1)].position);
			counter--;
		}
	}

	public void eat(Cake cake) {
		if(this.getEvolutionStage() != EvolutionStage.FEEDING_STAGE) {
			return;
		}

		int cakeEnergy = cake.getEnergyProvided();
		int growthFactor = 0;
		Segment current = this.head;
		Segment newTail;
		boolean similar = false;
		int size = this.positionsPreviouslyOccupied.size();
		this.stage = EvolutionStage.GROWING_STAGE;

		for (int i = 0; i < cakeEnergy && size > 0; i++) {
			for (int j = 0; j < this.getLength(); j++) {
				if (!(this.positionsPreviouslyOccupied.elementAt(size - 1).equals(current.position))){
					current = current.next;
				}
				else {
					similar = true;
					break;
				}
			}
			if (similar) {
				break;
			}

			int k = randNumGenerator.nextInt(GameColors.SEGMENT_COLORS.length);
			newTail = new Segment(this.positionsPreviouslyOccupied.pop(),GameColors.SEGMENT_COLORS[k]);
			size--;
			this.tail.next = newTail;
			this.tail = newTail;
			this.length++;
			if (this.getLength() >= this.goal) {
				this.stage = EvolutionStage.BUTTERFLY;
				return;
			}
			growthFactor++;
			current = this.head;
		}

		this.turnsNeededToDigest = cakeEnergy - growthFactor;
	}

	public class Segment {
		private Position position;
		private Color color;
		private Segment next;

		public Segment(Position p, Color c) {
			this.position = p;
			this.color = c;
		}
	}

	public String toString() {
		Segment s = this.head;
		String gus = "";
		while (s!=null) {
			String coloredPosition = GameColors.colorToANSIColor(s.color) +
					s.position.toString() + GameColors.colorToANSIColor(Color.WHITE);
			gus = coloredPosition + " " + gus;
			s = s.next;
		}
		return gus;
	}
}