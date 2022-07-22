package home.tommy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class PrimativeTest {
	public static void main(String[] str) throws Exception {
		// [[1,4,5],[1,3,4],[2,6]]
		ListNode ln15 = new ListNode(5);
		ListNode ln14 = new ListNode(4, ln15);
		ListNode ln11 = new ListNode(1, ln14);

		ListNode ln24 = new ListNode(4);
		ListNode ln23 = new ListNode(3, ln24);
		ListNode ln21 = new ListNode(1, ln23);

		ListNode ln36 = new ListNode(6);
		ListNode ln32 = new ListNode(2, ln36);

		List<ListNode> lnList = Arrays.asList(ln11, ln21, ln32);

		Solution solution = new Solution();
		solution.mergeKLists(lnList.toArray(new ListNode[0]));
	}
}

class FastSolution {
	public ListNode mergeKLists(ListNode[] lists) {
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		for (ListNode temp : lists) {
			while (temp != null) {
				pq.offer(temp.val);
				temp = temp.next;
			}
		}

		if (pq.isEmpty())
			return null;

		ListNode head = new ListNode(pq.poll());
		ListNode temp = head;
		while (!pq.isEmpty()) {
			temp.next = new ListNode(pq.poll());
			temp = temp.next;
		}
		return head;
	}
}

class Solution {
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists == null || lists.length == 0) {
			return null;
		}
		ArrayList<Integer> valList = new ArrayList<Integer>();
		for (ListNode ln : lists) {
			recursiveVal(valList, ln);
		}
		Collections.sort(valList);
		
        if (valList == null || valList.size() == 0) {
            return null;
        }		
		
		ListNode nextNode = null;
		ListNode thisNode = new ListNode(valList.get(0));
		nextNode = thisNode;

		for (int i = 1; i < valList.size(); i++) {
			nextNode.next = new ListNode(valList.get(i));
			nextNode = nextNode.next;
		}

		return thisNode;
	}

	public void recursiveVal(ArrayList<Integer> valList, ListNode node) {
		if (node != null) {
			valList.add(node.val);
			if (node.next != null) {
				recursiveVal(valList, node.next);
			}
		}
	}

}

class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}
}
