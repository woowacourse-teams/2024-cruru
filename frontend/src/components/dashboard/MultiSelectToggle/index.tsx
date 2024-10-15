import ToggleSwitch from '@components/_common/atoms/ToggleSwitch';

import S from './style';

interface MultiSelectToggleProps {
  isSelectMode: boolean;
  onToggle: () => void;
}

export default function MultiSelectToggle({ isSelectMode, onToggle }: MultiSelectToggleProps) {
  return (
    <S.ToggleWrapper>
      <S.ToggleLabel>여러 지원자 선택</S.ToggleLabel>
      <ToggleSwitch
        width="4rem"
        onChange={onToggle}
        isChecked={isSelectMode}
        isDisabled={false}
      />
    </S.ToggleWrapper>
  );
}
