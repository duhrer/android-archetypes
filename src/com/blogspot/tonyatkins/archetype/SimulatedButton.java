package com.blogspot.tonyatkins.archetype;

public class SimulatedButton implements Comparable {
	private final long id;
	private final String label;
	private final int color;
	private final int resource;
	private int sortOrder;

	public SimulatedButton(long id, String label, int color, int resource, int sortOrder) {
		this.id = id;
		this.label = label;
		this.color = color;
		this.resource = resource;
		this.sortOrder = sortOrder;
	}

	@Override
	public int compareTo(Object otherObject) {
		if (otherObject instanceof SimulatedButton) {
			SimulatedButton otherButton = (SimulatedButton) otherObject;
			return this.sortOrder - otherButton.sortOrder;
		}

		return 0;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getLabel() {
		return label;
	}

	public int getColor() {
		return color;
	}

	public int getResource() {
		return resource;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "SimulatedButton [id=" + id + ", label=" + label + ", sortOrder=" + sortOrder + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + color;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + resource;
		result = prime * result + sortOrder;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulatedButton other = (SimulatedButton) obj;
		if (color != other.color)
			return false;
		if (id != other.id)
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		}
		else if (!label.equals(other.label))
			return false;
		if (resource != other.resource)
			return false;
		if (sortOrder != other.sortOrder)
			return false;
		return true;
	}
}
