import CheckBox from '../CheckBox';
import S from './style';

interface Option {
  optionLabel: string;
  isChecked: boolean;
  onToggle: () => void;
}

interface CheckboxLabelField {
  label?: string;
  description?: string;
  disabled?: boolean;
  error?: string;
  required?: boolean;
  options: Option[];
}

export default function CheckboxLabelField({
  label,
  description,
  disabled = false,
  error,
  required = false,
  options,
}: CheckboxLabelField) {
  return (
    <S.Wrapper>
      <S.HeadWrapper>
        <S.HeadWrapper>
          {label && (
            <S.LabelWrapper>
              <S.Label disabled={!!disabled}>{label}</S.Label>
              {required && <S.Asterisk />}
            </S.LabelWrapper>
          )}
          {description && <S.DescriptionWrapper>{description}</S.DescriptionWrapper>}
        </S.HeadWrapper>
      </S.HeadWrapper>

      <S.OptionsWrapper>
        {options.map(({ optionLabel, isChecked, onToggle }, index) => (
          // eslint-disable-next-line react/no-array-index-key
          <S.Option key={index}>
            <CheckBox
              isChecked={isChecked}
              onToggle={onToggle}
              isDisabled={disabled}
            />
            <S.OptionLabel>{optionLabel}</S.OptionLabel>
          </S.Option>
        ))}
      </S.OptionsWrapper>

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
