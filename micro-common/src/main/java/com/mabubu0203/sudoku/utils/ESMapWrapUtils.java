package com.mabubu0203.sudoku.utils;

import com.mabubu0203.sudoku.enums.Selector;
import com.mabubu0203.sudoku.enums.Type;
import lombok.NoArgsConstructor;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.SortedMaps;
import org.eclipse.collections.impl.tuple.Tuples;

import java.util.Collections;

import static lombok.AccessLevel.PRIVATE;

/**
 * Mapを使用するUtil群です。<br>
 * .
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
public class ESMapWrapUtils {

  /**
   * リストボックスに使用するmapを返却します。 <br>
   * mapの要素は数独のタイプ種別を保持します。 <br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableMap<String, Integer> getSelectTypes() {
    MutableMap<String, Integer> map = Maps.mutable.empty();
    map.put("2*2", Type.SIZE4.getSize());
    map.put("3*3", Type.SIZE9.getSize());
    return map.toImmutable();
  }

  /**
   * リストボックスに使用するmapを返却します。<br>
   * mapの要素は数独の難易度を保持します。<br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableMap<String, String> getSelectLevels() {
    MutableMap<String, String> map = Maps.mutable.empty();
    map.put("イージー", "easy");
    map.put("ノーマル", "normal");
    map.put("ハード", "hard");
    return map.toImmutable();
  }

  /**
   * リストボックスに使用するmapを返却します。<br>
   * mapの要素は{No}の検索条件を保持します。<br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableSortedMap<String, Integer> getSelectorNo() {
    MutableSortedMap<String, Integer> map = SortedMaps.mutable.of(Collections.reverseOrder());
    setSelector(map, Selector.PERFECT_MATCH);
    setSelector(map, Selector.AROUND5);
    setSelector(map, Selector.MORE_BIG);
    setSelector(map, Selector.MORE_SMALL);
    return map.toImmutable();
  }

  /**
   * リストボックスに使用するmapを返却します。 <br>
   * mapの要素は{KeyHash}の検索条件を保持します。<br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableSortedMap<String, Integer> getSelectorKeyHash() {
    MutableSortedMap<String, Integer> map = SortedMaps.mutable.of(Collections.reverseOrder());
    setSelector(map, Selector.PERFECT_MATCH);
    setSelector(map, Selector.FORWARD_MATCH);
    setSelector(map, Selector.BACKWARD_MATCH);
    setSelector(map, Selector.PARTIAL_MATCH);
    return map.toImmutable();
  }

  /**
   * リストボックスに使用するmapを返却します。 <br>
   * mapの要素は{Score}の検索条件を保持します。<br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableSortedMap<String, Integer> getSelectorScore() {
    MutableSortedMap<String, Integer> map = SortedMaps.mutable.of(Collections.reverseOrder());
    setSelector(map, Selector.PERFECT_MATCH);
    setSelector(map, Selector.MORE_BIG);
    setSelector(map, Selector.MORE_SMALL);
    return map.toImmutable();
  }

  /**
   * リストボックスに使用するmapを返却します。<br>
   * mapの要素は{Name}の検索条件を保持します。<br>
   * .
   *
   * @return 変更不能のmapを返却します
   */
  public static ImmutableSortedMap<String, Integer> getSelectorName() {
    MutableSortedMap<String, Integer> map = SortedMaps.mutable.of(Collections.reverseOrder());
    setSelector(map, Selector.PERFECT_MATCH);
    setSelector(map, Selector.FORWARD_MATCH);
    setSelector(map, Selector.BACKWARD_MATCH);
    setSelector(map, Selector.PARTIAL_MATCH);
    return map.toImmutable();
  }

  private static void setSelector(MutableSortedMap<String, Integer> map, Selector selector) {
    Pair<String, Integer> keyValuePair = Tuples.pair(selector.getLabel(), selector.getValue());
    map.add(keyValuePair);
  }
}
