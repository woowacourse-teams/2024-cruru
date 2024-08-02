import S from './style';

interface RadioProps {
  isChecked: boolean;
  isDisabled?: boolean;
  diameter?: string;
  onChange: () => void;
}

export default function Radio({ isChecked, isDisabled = false, diameter, onChange }: RadioProps) {
  return (
    <S.RadioContainer onClick={onChange}>
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
