/**
 * 
 */
package gui;

/**
 * @author jonas
 *
 */
public enum Property {
	GRENADES{
		@Override
		public String toString() {
			return "Grenades";
		}
	},
	WALLS{
		@Override
		public String toString() {
			return "Walls";
		}
	},
	CURRENT_PLAYER{
		@Override
		public String toString() {
			return "CurrentPlayer";
		}
	},
}
