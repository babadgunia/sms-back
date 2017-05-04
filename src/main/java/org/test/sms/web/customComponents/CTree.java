package org.test.sms.web.customComponents;

import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CTree extends Tree {

    private ArrayList<CTreeItem<?>> parents = new ArrayList<>();

    public CTree(String caption) {
        super(caption);
        setMultiSelect(true);
        setMultiselectMode(MultiSelectMode.SIMPLE);
        setDragMode(TreeDragMode.NODE);
        setDropHandler(new CustomDropHandler());
        setImmediate(true);
        addItemClickListener(e -> {
            CTreeItem<?> item = (CTreeItem<?>) e.getItemId();
            item.setSelected(!item.isSelected());
            CTreeItem<?> parent = getParent(item);
            if (Objects.nonNull(parent)) {
                if (hasChildrenSelected(parent)) {
                    select(parent);
                } else {
                    unselect(parent);
                }
            }
        });
    }

    private boolean hasChildrenSelected(CTreeItem<?> parent) {
        for (Object item : getChildren(parent)) {
            CTreeItem<?> child = (CTreeItem<?>) item;
            if (child.isSelected()) {
                return true;
            }
        }
        return false;
    }

    public CTreeItem<?> addParent(CTreeItem<?> item) {
        parents.add(item);
        addItem(item);
        return item;
    }

    public void addChild(CTreeItem<?> child, CTreeItem<?> parent) {
        addItem(child);
        setParent(child, parent);
        setChildrenAllowed(child, false);
    }

    public void select(CTreeItem<?> item) {
        item.setSelected(true);
        super.select(item);
    }

    private CTreeItem<?> getParent(CTreeItem<?> child) {
        for (CTreeItem<?> parent : parents) {
            for (Object item : getChildren(parent)) {
                if (item == child) {
                    return parent;
                }
            }
        }
        return null;
    }

    public List<CTreeItem<?>> getParents() {
        return parents;
    }

    private CTree getOuter() {
        return this;
    }

    private class CustomDropHandler implements DropHandler {

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }

        @Override
        public void drop(DragAndDropEvent event) {
            Transferable t = event.getTransferable();
            if (t.getSourceComponent() != getOuter()) {
                return;
            }

            TreeTargetDetails target = (TreeTargetDetails) event.getTargetDetails();

            Object sourceItemId = t.getData("itemId");
            Object targetItemId = target.getItemIdOver();

            VerticalDropLocation location = target.getDropLocation();

            HierarchicalContainer container = (HierarchicalContainer) getContainerDataSource();
            CTreeItem<?> targetItem = (CTreeItem<?>) targetItemId;
            if (parents.contains(sourceItemId) && parents.contains(targetItem)) {
                if (location == VerticalDropLocation.TOP) {
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    container.moveAfterSibling(targetItemId, sourceItemId);
                    parents.remove(sourceItemId);
                    parents.add(parents.indexOf(targetItem), (CTreeItem<?>) sourceItemId);

                } else if (location == VerticalDropLocation.BOTTOM) {
                    container.moveAfterSibling(sourceItemId, targetItemId);
                    parents.remove(sourceItemId);
                    parents.add(parents.indexOf(targetItem) + 1, (CTreeItem<?>) sourceItemId);
                }
            }
        }
    }
}