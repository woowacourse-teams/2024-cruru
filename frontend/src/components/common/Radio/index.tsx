import S from './style';

interface RadioProps {
  isChecked: boolean;
  isDisabled?: boolean;
  diameter?: string;
  onToggle: () => void;
}

export default function Radio({ isChecked, isDisabled = false, onToggle, diameter }: RadioProps) {
  return (
    <S.RadioContainer onClick={onToggle}>
      <S.RadioOuter
        isDisabled={isDisabled}
        diameter={diameter}
        checked={isChecked}
      >
        <S.RadioInner
          checked={isChecked}
          isDisabled={isDisabled}
        />
      </S.RadioOuter>
    </S.RadioContainer>
  );
}
